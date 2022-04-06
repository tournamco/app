package co.tournam.schedule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.Text;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.headerDiscovery;
import co.tournam.ui.tournament_summary.TournamentSummaryListJoin;

public class DiscoveryActivity extends AppCompatActivity {

    Context context;
    private List<TournamentModel> tournamentList = new ArrayList<TournamentModel>();
    private Resources res = getResources();
    private Drawable qr = ResourcesCompat.getDrawable(res, R.drawable.qr_icon, null);
    private Drawable nfc = ResourcesCompat.getDrawable(res, R.mipmap.ic_nfc, null);

    private LinearLayout mainDiscoveryHeaderLayout;
    private LinearLayout joinWithQRLayout;
    private LinearLayout joinWithNFCLayout;
    private LinearLayout localOnlineSliderLayout;
    private LinearLayout tournamentHeaderLayout;
    private LinearLayout tournamentListLayout;

    private boolean isLocal;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        this.context = this.getApplicationContext();

        setMainDiscoveryHeaderLayout();
        setJoinWithQRLayout();
        setJoinWithNFCLayout();
        setLocalOnlineSliderLayout();
        setTournamentHeaderLayout();
        setTournamentListLayout();

    }

    public void setMainDiscoveryHeaderLayout() {

        mainDiscoveryHeaderLayout = (LinearLayout) findViewById(R.id.discovery_main_header);
        headerDiscovery header = new headerDiscovery(this.context, "Discovery");
        mainDiscoveryHeaderLayout.addView(header);

    }

    public void setJoinWithQRLayout() {

        joinWithQRLayout = (LinearLayout) findViewById(R.id.discovery_join_qr);
        DefaultButtonIMG qrJoin = new DefaultButtonIMG(this.context, "Join using QR code", qr);
        joinWithQRLayout.addView(qrJoin);

    }

    public void setJoinWithNFCLayout() {

        joinWithNFCLayout = (LinearLayout) findViewById(R.id.discovery_join_nfc);
        DefaultButtonIMG nfcJoin = new DefaultButtonIMG(this.context, "Join using NFC", nfc);
        joinWithNFCLayout.addView(nfcJoin);


    }

    public void setLocalOnlineSliderLayout() {

        localOnlineSliderLayout = (LinearLayout) findViewById(R.id.discovery_slider);
        Slider slider = new Slider(this.context, true);
        localOnlineSliderLayout.addView(slider);
        slider.buttonLocal.setOnClickListener(v -> {
            slider.setButtons(false);
            this.isLocal = slider.getBool();
        });
        slider.buttonOnline.setOnClickListener(v -> {
            slider.setButtons(true);
            this.isLocal = slider.getBool();
        });

        this.isLocal = slider.getBool();


    }

    public void setTournamentHeaderLayout() {

        tournamentHeaderLayout = (LinearLayout) findViewById(R.id.discovery_tourn_header);
        Text text = new Text(this.context, "tournaments");
        tournamentHeaderLayout.addView(text);

    }

    public void setTournamentListLayout() {

        tournamentListLayout = (LinearLayout) findViewById(R.id.discovery_tourn_list);
        updateList();
        TournamentSummaryListJoin list = new TournamentSummaryListJoin(this.context, tournamentList);
        tournamentListLayout.addView(list);

    }

    public List<TournamentModel> getOnline(Context context) {
        // TODO: GET TournamentLIST FROM SERVER

        List<TournamentModel> output = new ArrayList<TournamentModel>();

        TournamentHandler.discoveryOnline(0, 10, new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {

                Toast.makeText(context, "Successful retrieval", Toast.LENGTH_LONG).show();
                output.addAll(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
                Toast.makeText(context, "NOT Successful retrieval", Toast.LENGTH_LONG).show();
            }
        });

        return output;
    }

    public List<TournamentModel> getOffline(Context context, String location) {
        // TODO: GET TournamentLIST FROM SERVER

        List<TournamentModel> output = new ArrayList<TournamentModel>();

        TournamentHandler.discoveryLocal(location, 5, 0, 10,
                new TournamentHandler.DiscoverComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {
                Toast.makeText(context, "Successful retrieval", Toast.LENGTH_LONG).show();
                output.addAll(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
                Toast.makeText(context, "NOT Successful retrieval", Toast.LENGTH_LONG).show();
            }
        });

        return output;
    }

    public void updateList() {

        if(this.isLocal) {
            this.tournamentList = getOffline(this.context, "TODO LOCATION");
        } else {
            this.tournamentList = getOnline(this.context);
        }
    }

}



//        DefaultButton button = new DefaultButton(this.getContext(), "Create");
//
//        headerDiscovery header = new headerDiscovery(this.getContext(), "Discovery");
//
//        TournamentSummaryListJoin list = new TournamentSummaryListJoin(this.getContext(), fake);
//
//
//        Slider slider = new Slider(this.getContext(), true);
//
//
//        DefaultButtonIMG qrJoin = new DefaultButtonIMG(this.getContext(), "Join using QR code", qr);
//
//        qrJoin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,6));
//
//        DefaultButtonIMG nfcJoin = new DefaultButtonIMG(this.getContext(), "Join using NFC", nfc);
//
//        qrJoin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,6));
//
//        Text text = new Text(this.getContext(), "Tournaments");
//
//        text.setLayoutParams(param);
//        this.addView(header);
//        this.addView(qrJoin);
//        this.addView(nfcJoin);
//        this.addView(slider,3);
//
//        this.addView(text,4);
//
//        this.addView(list);
//
//        header.button.setOnClickListener(view -> {
//            context.startActivity(new Intent(context, CreateTournamentActivity.class));
//        });
