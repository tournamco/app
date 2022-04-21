package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.matchlist.AlteredMatchList;
import co.tournam.ui.roundbar.RoundBar;


public class StageActivity extends AppCompatActivity {

    //Variable Declarations
    Context context;
    private String tournamentID;
    private int stageIndex;
    private LinearLayout mainHeader;
    private LinearLayout roundBarLayout;
    private LinearLayout alteredMatchListLayout;
    private StageModel stage;
    private String tournamentName;
    private int currentRound;


    //On create method of the Stage Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();

        if (b != null) {
            tournamentID = b.getString("tournamentID");
            stageIndex = b.getInt("stageIndex");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_view);
        this.context = this.getApplicationContext();


        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                setMainStage(tournament.getStages().get(stageIndex), tournament.getName());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    //Calls setup and build functions of the activity
    public void setMainStage(StageModel stage, String tournamentName) {
        this.stage = stage;
        this.tournamentName = tournamentName;
        setMainHeader();
        setRoundBarLayout();
        setAlteredMatchListLayout();
    }

    //Sets up and builds the Header of the Activity
    public void setMainHeader() {
        mainHeader = findViewById(R.id.Main_Header_Layout_stageview);
        SmallHeader header = new SmallHeader(context, this.stage.getName(),
                this.tournamentName, () -> {
            finish();
        });
        mainHeader.addView(header);


    }

    //Setups up the scrollbar of Rounds for the Stage
    public void setRoundBarLayout() {
        roundBarLayout = (LinearLayout) findViewById(R.id.roundbar_layout_stageview);
        RoundBar bar = new RoundBar(context, this.stage.getRounds(), round -> {
            this.currentRound = stage.getRounds().indexOf(round);
            refreshMatches();
        });

        roundBarLayout.addView(bar);


    }

    //Sets up the linear layout for the match list and calls the refreshMatches() method
    public void setAlteredMatchListLayout() {
        alteredMatchListLayout = (LinearLayout) findViewById(R.id.altered_match_list_stageview);
        refreshMatches();
    }

    //Gets the matches of the currently selected round and passes them to the addMatches() method
    public void refreshMatches() {
        TournamentHandler.listRoundMatches(this.tournamentID, this.stageIndex, this.currentRound, new TournamentHandler.ListRoundMatchesComplete() {
            @Override
            public void success(List<MatchModel> matches) {
                addMatches(matches);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    //Add the matches to and build the match list component
    public void addMatches(List<MatchModel> matches) {
        alteredMatchListLayout.removeAllViews();
        AlteredMatchList matchList = new AlteredMatchList(context, matches);
        alteredMatchListLayout.addView(matchList);
    }

}
