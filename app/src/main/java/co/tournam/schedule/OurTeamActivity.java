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
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.textentry.TextEntry;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class OurTeamActivity extends AppCompatActivity {

    public OurTeamActivity(String teamID, String tournamentID) {
        this.teamID = teamID;
        this.tournamentID = tournamentID;
    }

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);
        context = this.getApplicationContext();

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

        theManageButton.setOnClickListener(v -> startActivity(new Intent(OurTeamActivity.this, ManageTeamActivity.class)));
    }

    public void setTournamentBanner() {

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);

        TournamentHandler.info(this.tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                tournamentBannerLayout.addView( new TournamentSummaryListItem(
                        context,
                        tournament));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });


    }

    public void setTeamIcon() {

        teamIconLayout = (LinearLayout) findViewById(R.id.teamIcon);

        TeamHandler.info(this.teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                teamIconLayout.addView( new ImageListItem(
                        context, ImageLoader.loadImage(team.getIcon(), context)
                ));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });

    }

    public void setTeamName() {
        teamNameLayout = (LinearLayout) findViewById(R.id.teamName);

        TeamHandler.info(this.teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                teamNameLayout.addView( new TextEntry(
                        context, team.getName(), false
                ));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });


    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.MEMBERS
        ));
    }

    public void setTeamMembers() {
        teamMemberLayout = (LinearLayout) findViewById(R.id.teamMembers);
        List<TeamModel> teams = new ArrayList<TeamModel>();
        TeamHandler.info(teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                teams.add(team);
            }

            @Override
            public void failure(ApiErrors error, String message) {

            }
        });
        teamMemberLayout.addView(new TeamMembers(
                context,
                teams
        ));
    }

    public void setSecondHeader() {

        secondHeaderLayout = (LinearLayout) findViewById(R.id.headerTwo);
        secondHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.MATCHES
        ));
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
