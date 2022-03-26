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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);
        FakeData data = new FakeData();
        context = this.getApplicationContext();

        LinearLayout layout = (LinearLayout) findViewById(R.id.tournamentLogo);
        layout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.search_playstore);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.teamIcon);
        layout2.addView( new ImageListItem(
                context, bm
                ));
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.teamName);
        layout3.addView( new TextEntry(
                context, "Name", false
        ));
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.headerOne);
        layout4.addView( new header(
                context,
                null,
                headerTitle.INVITE
        ));
        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        Drawable phone_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);;
        Drawable link_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);;
        LinearLayout layout5 = (LinearLayout) findViewById(R.id.Buttons);
        layout5.addView( new DefaultButtonIMG(
                context,
                "Use QR",
                qr_icon
        ));
        layout5.addView( new DefaultButtonIMG(
                context,
                "Use link",
                phone_icon
        ));
        layout5.addView( new DefaultButtonIMG(
                context,
                "Use NFC",
                link_icon
        ));
        LinearLayout layout6 = (LinearLayout) findViewById(R.id.headerTwo);
        layout6.addView( new header(
                context,
                data.tournament.getTeams().get(0),
                headerTitle.MEMBERS
        ));
        LinearLayout layout7 = (LinearLayout) findViewById(R.id.members);
        layout7.addView( new Members(
                this.getApplicationContext(),
                data.tournament.getTeams().get(0)));


    }


}



