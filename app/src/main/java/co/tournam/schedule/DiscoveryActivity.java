package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
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
    private LinearLayout mainDiscoveryHeaderLayout;
    private LinearLayout joinWithQRLayout;
    private LinearLayout joinWithNFCLayout;
    private LinearLayout localOnlineSliderLayout;
    private LinearLayout tournamentHeaderLayout;
    private LinearLayout tournamentListLayout;
    private boolean isLocal;

    private TournamentSummaryListJoin Thelist;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        this.context = this.getApplicationContext();
        this.qr = ResourcesCompat.getDrawable(this.getResources(), R.drawable.qr_icon, null);
        this.nfc = ResourcesCompat.getDrawable(this.getResources(), R.drawable.nfc_icon, null);

        setMainDiscoveryHeaderLayout();
        setJoinWithQRLayout();
        setJoinWithNFCLayout();
        setLocalOnlineSliderLayout();
        setTournamentHeaderLayout();
        setTournamentListLayout();
        setNavigationBar();

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
    }

    public void setJoinWithNFCLayout() {
        joinWithNFCLayout = (LinearLayout) findViewById(R.id.discovery_join_nfc);
        DefaultButtonIMG nfcJoin = new DefaultButtonIMG(context,
                "                  Join using NFC                  ", nfc);
        joinWithNFCLayout.addView(nfcJoin);
    }

    public void setLocalOnlineSliderLayout() {
        localOnlineSliderLayout = (LinearLayout) findViewById(R.id.discovery_slider);
        Slider slider = new Slider(context, true);
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
        Log.wtf("Set Tournaments was called.", "Executed");
        Thelist.clearList();
        Thelist.addTournaments(tournaments);
    }


    public void getOnline(Context context) {
        this.tournamentList.clear();
        TournamentHandler.discoveryOnline(0, 10, new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {

                Toast.makeText(context, "Successful retrieval", Toast.LENGTH_LONG).show();
                Log.wtf("Gotten List length", String.valueOf(tournaments.size()));
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
        TournamentHandler.discoveryLocal(location, 1, 0, 10,
                new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {
                Toast.makeText(context, "Successful retrieval", Toast.LENGTH_LONG).show();
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
            getOffline(context, "TODO LOCATION");
        } else {
            getOnline(context);
        }
    }

    public void setNavigationBar() {
        BottomNavigationView bottomNav = findViewById(R.id.discovery_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_discovery);
        bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    startActivity(new Intent(DiscoveryActivity.this, TournamentActivity.class));
                    break;
                case R.id.navigation_discovery:
                    startActivity(new Intent(DiscoveryActivity.this, DiscoveryActivity.class));
                    break;
                case R.id.navigation_schedule:
                    startActivity(new Intent(DiscoveryActivity.this, ScheduleActivity.class));
                    break;
            }

            return true;
        });
    }
}