package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.FakeData;
import co.tournam.models.TeamModel;
import co.tournam.schedule.schedule.Schedule;
import co.tournam.ui.big_header;
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
    private LinearLayout manageTeamButton
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
        manageTeamButton.addView( new DefaultButton(
                context,
                "Manage"
        ));
    }

    public void setTournamentBanner() {

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
    }

    public void setTeamIcon() {
        //TODO Change once we can use backend Images.
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.search_playstore);
        teamIconLayout = (LinearLayout) findViewById(R.id.teamIcon);
        teamIconLayout.addView( new ImageListItem(
                context, bm
        ));
    }

    public void setTeamName() {

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
        teamMemberLayout = (LinearLayout) findViewById(R.id.teamMembers);
        List<TeamModel> team = new ArrayList<TeamModel>();
        team.add(data.tournament.getTeams().get(0));
        teamMemberLayout.addView(new TeamMembers(
           context,
           team
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

        matchList = (LinearLayout) findViewById(R.id.matchList);
        matchList.addView(new SummaryMatchList(
                context,
                data.tournament.getMatches(data.team1)
        ));
    }
}
