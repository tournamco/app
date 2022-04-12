package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.list.UserList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.textentry.TextEntry;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class OurTeamActivity extends AppCompatActivity {

    Context context;
    private LinearLayout manageTeamButton;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout teamIconLayout;
    private LinearLayout teamNameLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout teamMemberLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout matchList;
    private String teamID;
    private String tournamentID;
    private TournamentModel tournament;
    private TeamModel team;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);
        context = this.getApplicationContext();

        Bundle b = getIntent().getExtras();

        if(b != null) {
            teamID = b.getString("teamid");
            tournamentID = b.getString("tournamentid");
        }

        loadTournament();
    }

    public void loadTournament() {
        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                OurTeamActivity.this.tournament = tournament;
                loadTeam();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void loadTeam() {
        TeamHandler.info(teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                OurTeamActivity.this.team = team;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        setManageTeamButton();
        setTournamentBanner();
        setTeamIcon();
        setTeamName();
        setFirstHeader();
        setTeamMembers();
        setSecondHeader();
        setMatchList();
    }

    public void setManageTeamButton() {
        manageTeamButton = (LinearLayout) findViewById(R.id.manageButton);
        DefaultButton theManageButton = new DefaultButton(context, "Manage");
        manageTeamButton.addView(theManageButton);

        theManageButton.button.setOnClickListener(v -> startActivity(new Intent(OurTeamActivity.this, ManageTeamActivity.class)));
    }

    public void setTournamentBanner() {
        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(context, tournament));
    }

    public void setTeamIcon() {
        teamIconLayout = (LinearLayout) findViewById(R.id.teamIcon);
        teamIconLayout.addView( new ImageListItem(
                context, ImageLoader.loadImage(team.getIcon(), context)
        ));
    }

    public void setTeamName() {
        teamNameLayout = (LinearLayout) findViewById(R.id.teamName);
        teamNameLayout.addView( new TextEntry(
                context, team.getName(), false
        ));
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView(new DefaultTitle("Members", context));
    }

    public void setTeamMembers() {
        teamMemberLayout = (LinearLayout) findViewById(R.id.teamMembers);
        teamMemberLayout.addView(new UserList());
    }

    public void setSecondHeader() {

        secondHeaderLayout = (LinearLayout) findViewById(R.id.headerTwo);
        secondHeaderLayout.addView(new DefaultTitle("Matches", context));
    }

    public void setMatchList() {

        TeamHandler.listMatches(0, 10, new TeamHandler.ListMatchesComplete() {
            @Override
            public void success(List<MatchModel> matches) {
                OurTeamActivity.this.buildMatchSummaryList(matches, context);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });


    }

    public void buildMatchSummaryList(List<MatchModel> matches, Context context) {
        matchList = (LinearLayout) findViewById(R.id.matchList);
        matchList.addView(new SummaryMatchList(
                context,
                matches
        ));
    }
}
