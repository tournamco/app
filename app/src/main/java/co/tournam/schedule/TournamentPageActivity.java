package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.header.teamsInTournamentHeader;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.stagelist.StageList;

public class TournamentPageActivity extends AppCompatActivity {

    private TournamentModel tournamentModel;
    private Context context;
    private ImageView tournamentBanner;
    private TextView gameName;
    private LinearLayout firstHeader;
    private LinearLayout stages;
    private LinearLayout secondHeader;
    private LinearLayout teams;
    private LinearLayout matchesHeader;
    private LinearLayout matchView;
    private List<MatchModel> matchModels;

    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        String tournamentID = null;
        if(b != null) {
            tournamentID = b.getString("key");
        }
        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                tournamentModel = tournament;
            }

            @Override
            public void failure(ApiErrors error, String message) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_tournament_page);
        setBanner(context);
        setGameName(context);
        firstHeader = findViewById(R.id.stageHeader);
        stages = findViewById(R.id.stages);
        firstHeader.addView(new header(context, null, headerTitle.STAGES));
        setStages(context);
        secondHeader = findViewById(R.id.teamsHeader);
        teams = findViewById(R.id.teams);
        secondHeader.addView(new teamsInTournamentHeader(context, tournamentModel ));
        setTeams(context);
        matchesHeader = findViewById(R.id.matchesHeader);
        matchView = findViewById(R.id.matchView);
        matchesHeader.addView(new header(context, null, headerTitle.MATCHES));
        setMatches(context);
    }


    private void setBanner(Context context) {
        tournamentBanner = findViewById(R.id.tournamentBanner);
        tournamentBanner.setImageBitmap(ImageLoader.loadImage(tournamentModel.getBanner(), context));
    }

    private void setGameName(Context context) {
        gameName = findViewById(R.id.text);
        gameName.setText(tournamentModel.getGame());
    }

    private void setStages(Context context) {
        stages.removeAllViews();
        stages.addView(new StageList(context, (ArrayList<StageModel>) tournamentModel.getStages(), tournamentModel));
    }

    private void setTeams(Context context) {
        teams.removeAllViews();
        List<String> team = tournamentModel.getTeams();
        teams.addView(new TeamList(context, team, "Remove"));
    }

    private void setMatches(Context context) {
        matchView.removeAllViews();
        TournamentHandler.listMatches(tournamentModel.getId(),
                new TournamentHandler.ListMatchesComplete() {
                    @Override
                    public void success(List<MatchModel> matches) {
                        matchModels = matches;
                    }

                    @Override
                    public void failure(ApiErrors error, String message) {

                    }
                });
        matchView.addView(new SummaryMatchList(context, matchModels));
    }
}


