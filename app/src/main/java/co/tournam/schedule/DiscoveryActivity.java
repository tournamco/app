package co.tournam.schedule;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.DiscoveryHeader;
import co.tournam.ui.textentry.Text;
import co.tournam.ui.tournament_summary.TournamentSummaryListJoin;

public class DiscoveryActivity extends AppCompatActivity {

    Context context;
    private List<TournamentModel> tournamentList = new ArrayList<TournamentModel>();

    Drawable nfc;
    Drawable qr;
    protected LocationManager locationManager;
    private LinearLayout mainDiscoveryHeaderLayout;
    private LinearLayout joinWithQRLayout;
    private LinearLayout joinWithNFCLayout;
    private LinearLayout localOnlineSliderLayout;
    private LinearLayout tournamentHeaderLayout;
    private LinearLayout tournamentListLayout;
    private TournamentModel.TournamentLocation location;
    private boolean isLocal;
    private String currentLocation = "0, 0";
    private TournamentSummaryListJoin Thelist;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        this.context = this.getApplicationContext();
        this.qr = ResourcesCompat.getDrawable(this.getResources(), R.drawable.qr_icon, null);
        this.nfc = ResourcesCompat.getDrawable(this.getResources(), R.drawable.nfc_icon, null);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DiscoveryActivity.this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 101);

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                DiscoveryActivity.this.currentLocation = location.getLatitude() + ", " + location.getLongitude();
                DiscoveryActivity.this.location = new TournamentModel.TournamentLocation((float)location.getLongitude(), (float)location.getLatitude());
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });

        setMainDiscoveryHeaderLayout();
        setJoinWithQRLayout();
        setJoinWithNFCLayout();
        setLocalOnlineSliderLayout();
        setTournamentHeaderLayout();
        setTournamentListLayout();
        setNavigationBar();


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        if (result != null) {
            TeamHandler.joinByToken(result.getContents(), new TeamHandler.JoinCompleted() {
                @Override
                public void success(String teamId, boolean isLeader) {
                    Toast.makeText(DiscoveryActivity.this, "You have joined a new team.",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        }
    }

    public void setMainDiscoveryHeaderLayout() {
        mainDiscoveryHeaderLayout = (LinearLayout) findViewById(R.id.discovery_main_header);
        DiscoveryHeader header = new DiscoveryHeader(context);
        header.getButton().setOnClickListener(v -> context.startActivity(new Intent(context,
                CreateTournamentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        mainDiscoveryHeaderLayout.addView(header);
    }

    public void setJoinWithQRLayout() {
        joinWithQRLayout = (LinearLayout) findViewById(R.id.discovery_join_qr);
        DefaultButtonIMG qrJoin = new DefaultButtonIMG(context,
                "              Join using QR code              ", qr);
        joinWithQRLayout.addView(qrJoin);
        qrJoin.button.setOnClickListener(v -> {
            //QR Code scanner
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.initiateScan();
        });
    }

    public void setJoinWithNFCLayout() {
        //joinWithNFCLayout = (LinearLayout) findViewById(R.id.discovery_join_nfc);
        //DefaultButtonIMG nfcJoin = new DefaultButtonIMG(context,
        //        "                  Join using NFC                  ", nfc);
        //joinWithNFCLayout.addView(nfcJoin);
    }

    public void setLocalOnlineSliderLayout() {
        localOnlineSliderLayout = (LinearLayout) findViewById(R.id.discovery_slider);
        Slider slider = new Slider(context, false);
        localOnlineSliderLayout.addView(slider);
        slider.buttonLocal.setOnClickListener(v -> {
            slider.setButtons(true);
            this.isLocal = slider.getBool();
            updateList();
        });
        slider.buttonOnline.setOnClickListener(v -> {
            slider.setButtons(false);
            this.isLocal = slider.getBool();
            updateList();
//            Log.i("isLocal: ", String.valueOf(this.isLocal));
        });

        this.isLocal = slider.getBool();
    }

    public void setTournamentHeaderLayout() {
        tournamentHeaderLayout = (LinearLayout) findViewById(R.id.discovery_tourn_header);
        Text text = new Text(context, "tournaments");
        tournamentHeaderLayout.addView(text);
    }

    public void setTournamentListLayout() {
        tournamentListLayout = (LinearLayout) findViewById(R.id.discovery_tourn_list);
        updateList();

        Thelist = new TournamentSummaryListJoin(context, tournamentList);
        tournamentListLayout.addView(Thelist);
    }

    public void setTournaments(List<TournamentModel> tournaments) {
        Thelist.clearList();
        Thelist.addTournaments(tournaments, location);
    }


    public void getOnline(Context context) {
        this.tournamentList.clear();
        TournamentHandler.discoveryOnline(0, 10, new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {
                setTournaments(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
                Toast.makeText(context, "NOT Successful retrieval", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getOffline(Context context, String location) {
        this.tournamentList.clear();
        TournamentHandler.discoveryLocal(location, 100000, 0, 10,
                new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {
                setTournaments(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
                Toast.makeText(context, "NOT Successful retrieval", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateList() {
        if(this.isLocal) {
            getOffline(context, this.currentLocation);
            Log.wtf("Location", this.currentLocation);
        } else {
            getOnline(context);
        }
    }

    public void setNavigationBar() {
        BottomNavigationView bottomNav = findViewById(R.id.discovery_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_discovery);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    intent = new Intent(this, TournamentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
                case R.id.navigation_discovery:
                    //startActivity(new Intent(DiscoveryActivity.this, DiscoveryActivity.class));
                    break;
                case R.id.navigation_schedule:
                    intent = new Intent(this, ScheduleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
            }

            return true;
        });
    }


}