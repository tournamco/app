package co.tournam.schedule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import co.tournam.models.FakeData;
import co.tournam.schedule.schedule.Schedule;
import co.tournam.ui.big_header;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.list.AdapterTeam;
import co.tournam.ui.stageoptions.PoolStageOption;
import co.tournam.ui.stageoptions.PoolStageOptionBody;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class CreateTournamentActivity extends AppCompatActivity {

    Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout tournamentNameLayout;
    private LinearLayout tournamentGameNameLayout;
    private LinearLayout tournamentTeamSizeLayout;
    private LinearLayout tournamentColorPickerLayout;
    private LinearLayout stageOptionLayout;
    private LinearLayout createButton;
    private LinearLayout addButton;
    private LinearLayout backButton;
    private FakeData data;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        data = new FakeData();
        context = this.getApplicationContext();

        setBackButton();
        setCreateButton();
        setTournamentBanner();
        setTournamentName();
        setTournamentGameName();
        setTournamentTeamSize();
        setTournamentColorPicker();
        setFirstHeader();
        setStageOption();
        setAddButton();
    }

    public void setBackButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        backButton = (LinearLayout) findViewById(R.id.backButton);
        backButton.addView( new DefaultButtonIMG(
                context,
                "",
                qr_icon
        ));

    }

    public void setCreateButton() {

        createButton = (LinearLayout) findViewById(R.id.backButton);
        createButton.addView( new DefaultButtonFilled(
                context,
                "Create"
        ));

    }

    public void setTournamentBanner() {

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
    }

    public void setTournamentName() {

        tournamentNameLayout = (LinearLayout) findViewById(R.id.tournamentName);
        tournamentNameLayout.addView( new PoolStageOptionBody(
                context,
                "Name"));
    }

    public void setTournamentGameName() {

        tournamentGameNameLayout = (LinearLayout) findViewById(R.id.tournamentGameName);
        tournamentGameNameLayout.addView( new PoolStageOptionBody(
                context,
                "Game Name"));
    }

    public void setTournamentTeamSize() {

        tournamentTeamSizeLayout = (LinearLayout) findViewById(R.id.tournamentTeamSize);
        tournamentTeamSizeLayout.addView( new PoolStageOptionBody(
                context,
                "Team Size"));
    }

    public void setTournamentColorPicker() {

        tournamentColorPickerLayout = (LinearLayout) findViewById(R.id.tournamentColorPicker);
        tournamentColorPickerLayout.addView( new PoolStageOptionBody(
                context,
                "Color"));
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.STAGES
        ));
    }

    public void setStageOption() {

        stageOptionLayout = (LinearLayout) findViewById(R.id.teamlist);
        stageOptionLayout.addView( new PoolStageOption(
                context
        ));

    }

    public void setAddButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        addButton = (LinearLayout) findViewById(R.id.addStageOptionButton);
        addButton.addView( new DefaultButtonIMG(
                context,
                "",
                qr_icon
        ));

    }


}
