package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.header.TournamentPageHeader;
import co.tournam.ui.title.DefaultTitle;

public class TournamentPageActivity extends AppCompatActivity {

    private TournamentModel tournamentModel;
    private Context context;

    private LinearLayout customHeaderLayout;

    private Bitmap tournamentBanner;
    private TextView gameName;
    private LinearLayout firstHeader;
    private LinearLayout stages;
    private LinearLayout secondHeader;
    private LinearLayout teams;
    private LinearLayout matchesHeader;
    private LinearLayout matchView;


    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        String tournamentID = null;
        if(b != null) {
            tournamentID = b.getString("tournamentID");
        }
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_tournament_page);

        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                tournamentModel = tournament;
                setup();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void setup() {

        setCustomHeader();
        setGameName();
        setStagesHeader();

//        stages = findViewById(R.id.stages);

//        setStages(context);
//        secondHeader = findViewById(R.id.teamsHeader);
//        teams = findViewById(R.id.teams);
//        secondHeader.addView(new SubtextTitle("Teams", 1 + "/" + 1, context));
//        setTeams(context);
//        matchesHeader = findViewById(R.id.matchesHeader);
//        matchView = findViewById(R.id.matchView);
//        matchesHeader.addView(new DefaultTitle("Matches", context));
//        setMatches(context);
    }

    public void setCustomHeader() {
        customHeaderLayout = findViewById(R.id.tournament_page_custom_header);
        TournamentPageHeader customHeader = new TournamentPageHeader(context, "", tournamentModel);
        customHeader.backButton.setOnClickListener(v -> startActivity(new Intent(TournamentPageActivity.this, DiscoveryActivity.class)));
        customHeaderLayout.addView(customHeader);
    }


    private void setGameName() {
        gameName = findViewById(R.id.tournament_page_game_title);
        gameName.setText(tournamentModel.getGame());
    }

    private void setStagesHeader() {
        firstHeader = findViewById(R.id.tournament_page_stages_header);
        firstHeader.addView(new DefaultTitle("Stages", context));
    }
//
//    private void setStages(Context context) {
//        stages.removeAllViews();
//        stages.addView(new StageList(context, (ArrayList<StageModel>) tournamentModel.getStages(), tournamentModel));
//    }
//
//    private void setTeams(Context context) {
//        teams.removeAllViews();
//        List<String> team = tournamentModel.getTeams();
//        teams.addView(new TeamList(context, team, "Remove"));
//    }
//
//    private void setMatches(Context context) {
//        matchView.removeAllViews();
//        TournamentHandler.listMatches(tournamentModel.getId(),
//                new TournamentHandler.ListMatchesComplete() {
//                    @Override
//                    public void success(List<MatchModel> matches) {
//                        setMatchModels(matches);
//                    }
//
//                    @Override
//                    public void failure(ApiErrors error, String message) {
//                        System.err.println("API_ERROR: " + error.name() + " - " + message);
//                    }
//                });
//
//    }
//
//    public void setMatchModels(List<MatchModel> matches) {
//        matchView.addView(new SummaryMatchList(context, matches));
//    }

}


