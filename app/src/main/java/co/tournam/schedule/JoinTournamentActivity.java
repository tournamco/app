package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class JoinTournamentActivity extends AppCompatActivity {

    //Variable Declarations
    private Context context;
    private TournamentModel tournament;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout teamsListLayout;

    //On create method of the Join Tournament Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tournament);

        Bundle b = getIntent().getExtras();

        String tournamentID = null;
        if (b != null) {
            tournamentID = b.getString("tournamentid");
        }

        waitForTournamentInfo(tournamentID);

        context = this.getApplicationContext();
    }

    /**
     * Loads and sets the information of a tournament with its given ID, before
     * building the activity.
     *
     * @param tournamentID id of the tournament
     * @post this.tournament = TournamentModel tournament
     */
    private void waitForTournamentInfo(String tournamentID) {
        TournamentHandler.info(tournamentID, tournament -> {
            JoinTournamentActivity.this.tournament = tournament;
            build();
        });
    }

    //Builds the components of the activity
    private void build() {
        LinearLayout headerContainer = (LinearLayout) findViewById(R.id.header);

        if (tournament.isPublic()) {
            DefaultButton button = new DefaultButton(context, "Create");
            button.button.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("tournamentid", tournament.getId());
                Intent intent = new Intent(context, CreateTeamActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            });
            headerContainer.addView(new SmallHeader(context, "Join Tournament", () -> finish(), button));
        } else {
            headerContainer.addView(new SmallHeader(context, "Join Tournament", () -> finish()));
        }

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.banner);
        tournamentBannerLayout.addView(new TournamentSummaryListItem(context, tournament));

        teamsListLayout = (LinearLayout) findViewById(R.id.teams_list);

        LinearLayout title1 = (LinearLayout) findViewById(R.id.title1);
        title1.addView(new DefaultTitle("Teams", context));

        for (String teamId : tournament.getTeams()) {
            addTeam(teamId);
        }
    }

    //Adds a team to the current tournament with the given team id
    private void addTeam(String teamId) {
        TeamList list = new TeamList(context, new ArrayList<>(), "Join", team -> {
            TeamHandler.joinByTeamId(teamId, (teamId1, isLeader) -> {
                Toast.makeText(context, "Team Joined", Toast.LENGTH_LONG).show();
                finish();
                Bundle bundle = new Bundle();
                bundle.putString("teamid", teamId1);
                Intent intent = new Intent(context, OurTeamActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        });
        teamsListLayout.addView(list);

        TeamHandler.info(teamId, team -> {
            System.out.println("Team: " + team.getName() + " - " + team.isPublic() + " - " + team.getMembers().size() + " - " + tournament.getTeamSize() + " - " + (!team.isPublic() || team.getMembers().size() == tournament.getTeamSize()));

            if (!team.isPublic() || team.getMembers().size() == tournament.getTeamSize()) {
                return;
            }

            list.addTeam(team);
        });
    }
}
