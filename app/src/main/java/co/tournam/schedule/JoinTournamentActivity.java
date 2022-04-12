package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

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

    private Context context;
    private TournamentModel tournament;

    private LinearLayout tournamentBannerLayout;
    private ScrollView teamsListLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tournament);

        Bundle b = getIntent().getExtras();

        String tournamentID = null;
        if(b != null) {
            tournamentID = b.getString("tournamentid");
        }

        waitForTournamentInfo(tournamentID);

        context = this.getApplicationContext();
    }

    private void waitForTournamentInfo(String tournamentID) {
        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                JoinTournamentActivity.this.tournament = tournament;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        LinearLayout headerContainer = (LinearLayout)findViewById(R.id.header);

        if(tournament.isPublic()) {
            DefaultButton button = new DefaultButton(context, "Create");
            button.button.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("tournamentid", tournament.getId());
                Intent intent = new Intent(context, CreateTeamActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            });
            headerContainer.addView(new SmallHeader(context, "Join Tournament", () -> finish(), button));
        }
        else {
            headerContainer.addView(new SmallHeader(context, "Join Tournament", () -> finish()));
        }

        tournamentBannerLayout = (LinearLayout)findViewById(R.id.banner);
        tournamentBannerLayout.addView(new TournamentSummaryListItem(context, tournament));

        teamsListLayout = (ScrollView)findViewById(R.id.teams_list);

        LinearLayout title1 = (LinearLayout)findViewById(R.id.title1);
        title1.addView(new DefaultTitle("Teams", context));

        for(String teamId : tournament.getTeams()) {
            addTeam(teamId);
        }
    }

    private void addTeam(String teamId) {
        TeamList list = new TeamList(context, new ArrayList<>(), "Join", team -> {

        });
        teamsListLayout.addView(list);

        TeamHandler.info(teamId, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                if(!team.isPublic() || team.getMembers().size() == tournament.getTeamSize()) {
                    return;
                }

                list.addTeam(team);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }
}
