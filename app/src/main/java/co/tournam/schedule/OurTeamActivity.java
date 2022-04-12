package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UserHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.list.UserList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.textentry.TextEntry;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class OurTeamActivity extends AppCompatActivity {

    Context context;
    private String teamID;
    private String tournamentID;
    private TournamentModel tournament;
    private TeamModel team;
    private UserModel me;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);
        context = this.getApplicationContext();

        Bundle b = getIntent().getExtras();

        if(b != null) {
            teamID = b.getString("teamid");
        }

        System.out.println("teamID: " + teamID);

        loadTeam();
    }

    public void loadTeam() {
        TeamHandler.info(teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                OurTeamActivity.this.team = team;
                loadTournament();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void loadTournament() {
        TournamentHandler.info(team.getTournamentId(), new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                OurTeamActivity.this.tournament = tournament;
                loadMe();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void loadMe() {
        UserHandler.me(new UserHandler.MeCompleted() {
            @Override
            public void success(UserModel me) {
                OurTeamActivity.this.me = me;
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

        if(team.getLeader() != null && me.getId().equals(team.getLeader().getId())) {
            DefaultButton button = new DefaultButton(context, "Manage");
            button.button.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("teamid", team.getID());
                Intent intent = new Intent(context, ManageTeamActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            });
            headerContainer.addView(new SmallHeader(context, team.getName(), () -> finish(), button));
        }
        else {
            headerContainer.addView(new SmallHeader(context, team.getName(), () -> finish()));
        }

        LinearLayout tournamentBannerLayout = (LinearLayout) findViewById(R.id.banner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(context, tournament));

        ImageView teamIcon = (ImageView) findViewById(R.id.team_icon);
        new DownloadImageWorker(image -> teamIcon.setImageBitmap(image)).execute(team.getIcon());

        TextView teamName = (TextView) findViewById(R.id.team_name);
        teamName.setText(team.getName());

        LinearLayout membersHeaderLayout = (LinearLayout) findViewById(R.id.members_header);
        membersHeaderLayout.addView(new DefaultTitle("Members", context));

        LinearLayout teamMemberLayout = (LinearLayout) findViewById(R.id.user_list);
        teamMemberLayout.addView(new UserList(context, team.getMembers(), null, null));
    }
}
