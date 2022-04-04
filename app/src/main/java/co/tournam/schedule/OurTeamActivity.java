package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.models.FakeData;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.textentry.TextEntry;
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
    private FakeData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);
        data = new FakeData();
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
        tournamentBannerLayout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
    }

    public void setTeamIcon() {
        //TODO fromJSON working
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.search_playstore);
        teamIconLayout = (LinearLayout) findViewById(R.id.teamIcon);
        teamIconLayout.addView( new ImageListItem(
                context, bm
        ));
    }

    public void setTeamName() {
        //TODO fromJSON working
        teamNameLayout = (LinearLayout) findViewById(R.id.teamName);
        teamNameLayout.addView( new TextEntry(
                context, "Name", false
        ));

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
        //TODO fromJSON working
        teamMemberLayout = (LinearLayout) findViewById(R.id.teamMembers);
        TeamModel team = data.team1;
        List<TeamModel> teams = new ArrayList<TeamModel>();
        teams.add(team);
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
