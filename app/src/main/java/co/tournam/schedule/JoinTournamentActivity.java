package co.tournam.schedule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class JoinTournamentActivity extends AppCompatActivity {

    public JoinTournamentActivity(TournamentModel tournament) {
        this.tournament = tournament;
    }

    Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout teamsListLayout;
    private LinearLayout backButton;
    private TournamentModel tournament;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tournament);

        context = this.getApplicationContext();

        setBackButton();
        setTournamentBanner();
        setFirstHeader();
        setTeams();
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

    public void setTournamentBanner() {

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(
                context,
                tournament));
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.MEMBERS
        ));
    }

    public void setTeams() {

        teamsListLayout = (LinearLayout) findViewById(R.id.teamlist);

        teamsListLayout.addView( new TeamList(
                context,
                tournament.getTeams(),
                "Join"
        ));

    }


}
