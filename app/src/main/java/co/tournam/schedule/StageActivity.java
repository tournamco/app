package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.matchlist.AlteredMatchList;
import co.tournam.ui.roundbar.RoundBar;
import co.tournam.ui.roundbar.RoundBarItem;


public class StageActivity extends AppCompatActivity {

    Context context;
    private String tournamentID;
    private int stageIndex;

    private TextView mainHeader;
    private TextView subHeader;
    private LinearLayout roundBarLayout;
    private LinearLayout alteredMatchListLayout;

    private int currentRound;

    public StageActivity(String tournamentID, int stageIndex) {
        this.tournamentID = tournamentID;
        this.stageIndex = stageIndex;
    }

    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();

        if(b != null) {
            tournamentID = b.getString("key");
            stageIndex = b.getInt("keyTwo");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_view);
        this.context = this.getApplicationContext();

        setMainHeader();
        setSubHeader();
        setRoundBarLayout();
        setAlteredMatchListLayout();

    }

    public void setMainHeader() {
        mainHeader = (TextView) findViewById(R.id.Stage_Name_Text);

    }

    public void setSubHeader() {
        mainHeader = (TextView) findViewById(R.id.Tournament_Name_Text);

        TournamentHandler.info(this.tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                mainHeader.setText(tournament.getName());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });


    }

    public void setRoundBarLayout() {
        roundBarLayout = (LinearLayout) findViewById(R.id.roundbar_layout_stageview);

        TournamentHandler.info(this.tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                RoundBar bar = new RoundBar(context, tournament.getStages().get(stageIndex).getRounds());
                for (int k = 0; k < bar.getChildCount(); k++) {
                    int finalK = k;
                    ((RoundBarItem) bar.getChildAt(k)).button.setOnClickListener(v -> {
                        currentRound = finalK;
                    });
                }
                roundBarLayout.addView(bar);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setAlteredMatchListLayout() {
        alteredMatchListLayout = (LinearLayout) findViewById(R.id.altered_match_list_stageview);

        TournamentHandler.listRoundMatches(this.tournamentID, this.stageIndex, this.currentRound, new TournamentHandler.ListRoundMatchesComplete() {
            @Override
            public void success(List<MatchModel> matches) {
                AlteredMatchList matchList = new AlteredMatchList(context, matches);
                alteredMatchListLayout.addView(matchList);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });



    }

}
