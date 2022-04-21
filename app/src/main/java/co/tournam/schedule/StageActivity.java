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

    Context context;
    private String tournamentID;
    private int stageIndex;

    private LinearLayout mainHeader;
    private LinearLayout roundBarLayout;
    private LinearLayout alteredMatchListLayout;
    private StageModel stage;
    private String tournamentName;

    private int currentRound;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();

        if (b != null) {
            tournamentID = b.getString("tournamentID");
            stageIndex = b.getInt("stageIndex");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_view);
        this.context = this.getApplicationContext();


        TournamentHandler.info(tournamentID,
                tournament -> setMainStage(tournament.getStages().get(stageIndex), tournament.getName()));
    }

    public void setMainStage(StageModel stage, String tournamentName) {
        this.stage = stage;
        this.tournamentName = tournamentName;
        setMainHeader();
        setRoundBarLayout();
        setAlteredMatchListLayout();
    }


    public void setMainHeader() {
        mainHeader = findViewById(R.id.Main_Header_Layout_stageview);
        SmallHeader header = new SmallHeader(context, this.stage.getName(),
                this.tournamentName, () -> {
            finish();
        });
        mainHeader.addView(header);


    }

    public void setRoundBarLayout() {
        roundBarLayout = (LinearLayout) findViewById(R.id.roundbar_layout_stageview);
        RoundBar bar = new RoundBar(context, this.stage.getRounds(), round -> {
            this.currentRound = stage.getRounds().indexOf(round);
            refreshMatches();
        });

        roundBarLayout.addView(bar);


    }

    public void setAlteredMatchListLayout() {
        alteredMatchListLayout = (LinearLayout) findViewById(R.id.altered_match_list_stageview);
        refreshMatches();
    }

    public void refreshMatches() {
        TournamentHandler.listRoundMatches(this.tournamentID, this.stageIndex, this.currentRound,
                matches -> addMatches(matches));
    }

    public void addMatches(List<MatchModel> matches) {
        alteredMatchListLayout.removeAllViews();
        AlteredMatchList matchList = new AlteredMatchList(context, matches);
        alteredMatchListLayout.addView(matchList);
    }

}
