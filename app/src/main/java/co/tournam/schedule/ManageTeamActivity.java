package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

import co.tournam.members.Members;
import co.tournam.models.FakeData;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.textentry.TextEntry;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class ManageTeamActivity extends AppCompatActivity {

    Context context;
    private LinearLayout tournamentLogoLayout;
    private LinearLayout teamIconLayout;
    private LinearLayout teamNameLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout buttonsLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout membersLayout;
    private FakeData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);
        data = new FakeData();
        context = this.getApplicationContext();

        setTournamentLogo();
        setTeamIcon();
        setTeamName();
        setFirstHeader();
        setButtons();
        setSecondHeader();
        setMembers();
    }

    private void setTournamentLogo() {
        tournamentLogoLayout = (LinearLayout) findViewById(R.id.tournamentLogo);
        tournamentLogoLayout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
    }

    private void setTeamIcon() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.search_playstore);
        teamIconLayout = (LinearLayout) findViewById(R.id.teamIcon);
        teamIconLayout.addView( new ImageListItem(
                context, bm
        ));
    }

    private void setTeamName() {
        teamNameLayout = (LinearLayout) findViewById(R.id.teamName);
        teamNameLayout.addView( new TextEntry(
                context, "Name", false
        ));
    }

    private void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.INVITE
        ));
    }

    private void setButtons() {
        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        Drawable phone_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);;
        Drawable link_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);;
        buttonsLayout = (LinearLayout) findViewById(R.id.Buttons);
        buttonsLayout.addView( new DefaultButtonIMG(
                context,
                "Use QR",
                qr_icon
        ));
        buttonsLayout.addView( new DefaultButtonIMG(
                context,
                "Use link",
                phone_icon
        ));
        buttonsLayout.addView( new DefaultButtonIMG(
                context,
                "Use NFC",
                link_icon
        ));
    }

    private void setSecondHeader() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.headerTwo);
       /* secondHeaderLayout.addView( new header(
                context,
                data.tournament.getTeams().get(0),
                headerTitle.MEMBERS
      )); */
    }

    private void setMembers() {
        membersLayout = (LinearLayout) findViewById(R.id.members);
      /*  membersLayout.addView( new Members(
                this.getApplicationContext(),
                data.tournament.getTeams().get(0)));*/
    }
}



