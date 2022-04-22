package co.tournam.schedule;

import android.content.Context;
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
import co.tournam.api.UserHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.models.StageModel;
import co.tournam.ui.header.TournamentPageHeader;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.stagelist.StageList;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.title.SubtextTitle;

public class TournamentPageActivity extends AppCompatActivity {

    //Variable Declarations
    private TournamentModel tournamentModel;
    private UserModel meModel;
    private Context context;
    private LinearLayout customHeaderLayout;
    private TextView gameName;
    private LinearLayout firstHeader;
    private LinearLayout stages;
    private LinearLayout secondHeader;
    private LinearLayout teams;
    private LinearLayout matchesHeader;
    private LinearLayout matchView;

    //On create method of the Tournament Details Page Activity
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        String tournamentID = null;
        if (b != null) {
            tournamentID = b.getString("tournamentid");
        }
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_tournament_page);

        loadTournament(tournamentID);
    }

    /**
     * Load tournament method asks the server for the tournament information
     * through its ID and sets it as the global tournament model.
     * Furthermore, it calls the loadMe() method.
     *
     * @param tournamentId id of the tournament
     * @post this.tournamentModel = TournamentModel tournament
     */
    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, tournament -> {
            tournamentModel = tournament;
            loadMe();
        });
    }

    /**
     * Load the information of the logged in user through a request to the server and then sets it
     * as the global user model. Furthermore, it also calls the setup() function.
     *
     * @post this.meModel = UserModel me
     */
    private void loadMe() {
        UserHandler.me(me -> {
            meModel = me;
            setup();
        });
    }

    //Calls all setup functions for building the activity
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

    //Sets up and builds the header of the Activity
    public void setCustomHeader() {
        customHeaderLayout = findViewById(R.id.tournament_page_custom_header);
        TournamentPageHeader customHeader = new TournamentPageHeader(context, "", tournamentModel, meModel);
        customHeader.backButton.setOnClickListener(v -> finish());
        customHeaderLayout.addView(customHeader);
    }

    //Sets up and builds the title displaying the game of the tournament
    private void setGameName() {
        gameName = findViewById(R.id.tournament_page_game_title);
        gameName.setText(tournamentModel.getGame());
    }

    //Sets up and builds the header of the Stages section
    private void setStagesHeader() {
        firstHeader = findViewById(R.id.tournament_page_stages_header);
        firstHeader.addView(new DefaultTitle("Stages", context));
    }

    //Sets up and builds the stage list section
    private void setStages() {
        stages = findViewById(R.id.tournament_page_stage_list);
        stages.removeAllViews();
        stages.addView(new StageList(context, (ArrayList<StageModel>) tournamentModel.getStages(), tournamentModel));
    }

    //Sets up and builds the header of the Teams section
    private void setTeamsHeader() {
        secondHeader = findViewById(R.id.tournament_page_teams_header);
        secondHeader.addView(new SubtextTitle("Teams",
                tournamentModel.getCurrentAmountOfTeams() + "/" +
                        tournamentModel.getStages().get(0).getNumberOfParticipants(), context));
    }

    /**
     * Obtains the models of the teams competing in the tournament from the server and calls the
     * setTeamsList() method.
     */
    private void setTeams() {
        teams = findViewById(R.id.tournament_page_teams_list);
        teams.removeAllViews();
        TeamHandler.info(tournamentModel.getTeams(), teams -> {
            Log.wtf("Team List size", String.valueOf(teams.size()));
            setTeamsList(teams);
        });

    }

    /**
     * Sets the teams of the teams list, and if the user is the organizer of the tournament
     * then they are allowed to remove teams from the tournament
     */
    private void setTeamsList(List<TeamModel> teams) {
        if (meModel.getId().equals(tournamentModel.getOrganizer().getId())) {
            this.teams.addView(new TeamList(context, teams, "Remove", team -> {
                Toast.makeText(context, "Removed Team", Toast.LENGTH_LONG).show();
                TeamHandler.delete(team.getID(), () -> {
                    TournamentPageActivity.this.teams.removeAllViews();
                    setTeams();
                });
            }));
        } else {
            this.teams.addView(new TeamList(context, teams, null, null));
        }
    }

    //Sets up and builds the header of the Matches section
    private void setMatchesHeader() {
        matchesHeader = findViewById(R.id.tournament_page_matches_header);
        matchesHeader.addView(new DefaultTitle("Matches", context));
    }

    /**
     * Obtains the models of the matches coming up in the tournament from the server and calls the
     * setMatchModels() method.
     */
    private void setMatches() {
        matchView = findViewById(R.id.tournament_page_matches_list);
        matchView.removeAllViews();
        TournamentHandler.listMatches(tournamentModel.getId(), true,
                matches -> {
                    setMatchModels(matches);
                    Log.wtf("Match List size", String.valueOf(matches.size()));
                });

    }

    //Adds the matches to the list and builds the list.
    public void setMatchModels(List<MatchModel> matches) {
        matchView.addView(new SummaryMatchList(context, matches));
    }

}


