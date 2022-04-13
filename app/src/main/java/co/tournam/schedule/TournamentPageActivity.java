package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;
import co.tournam.ui.header.TournamentPageHeader;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.stagelist.StageList;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.title.SubtextTitle;

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
            tournamentID = b.getString("tournamentid");
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
        setStages();
        setTeamsHeader();
        setTeams();
        setMatchesHeader();
        setMatches();

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

    private void setStages() {
        stages = findViewById(R.id.tournament_page_stage_list);
        stages.removeAllViews();
        stages.addView(new StageList(context, (ArrayList<StageModel>) tournamentModel.getStages(), tournamentModel));
    }

    private void setTeamsHeader() {
        secondHeader = findViewById(R.id.tournament_page_teams_header);
        secondHeader.addView(new SubtextTitle("Teams",
                tournamentModel.getCurrentAmountOfTeams() + "/" +
                        tournamentModel.getStages().get(0).getNumberOfParticipants(), context));
    }


    private void setTeams() {
        teams = findViewById(R.id.tournament_page_teams_list);
        teams.removeAllViews();
        TeamHandler.info(tournamentModel.getTeams(), new TeamHandler.InfoArrayComplete() {
            @Override
            public void success(List<TeamModel> teams) {
                Log.wtf("Team List size", String.valueOf(teams.size()));
                setTeamsList(teams);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });

    }

    private void setTeamsList(List<TeamModel> teams) {
        this.teams.addView(new TeamList(context, teams, "Remove", team -> {
            Toast.makeText(context, "Removed Team", Toast.LENGTH_LONG).show();
        }));
    }

    private void setMatchesHeader() {
        //TODO Add slider to the header
        matchesHeader = findViewById(R.id.tournament_page_matches_header);
        matchesHeader.addView(new DefaultTitle("Matches", context));
    }


    private void setMatches() {
        matchView = findViewById(R.id.tournament_page_matches_list);
        matchView.removeAllViews();
        TournamentHandler.listMatches(tournamentModel.getId(), true,
                new TournamentHandler.ListMatchesComplete() {
                    @Override
                    public void success(List<MatchModel> matches) {
                        setMatchModels(matches);
                        Log.wtf("Match List size", String.valueOf(matches.size()));

                    }

                    @Override
                    public void failure(ApiErrors error, String message) {
                        System.err.println("API_ERROR: " + error.name() + " - " + message);
                    }
                });

    }

    public void setMatchModels(List<MatchModel> matches) {
        matchView.addView(new SummaryMatchList(context, matches));
    }

}


