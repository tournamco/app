package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.header.TournamentHeader;
import co.tournam.ui.tournament_summary.TournamentSummaryList;

public class TournamentActivity extends AppCompatActivity {

    //Variable Declarations
    private Context context;
    private LinearLayout tournamentsLayout;
    private TournamentSummaryList tournamentsList;
    private final int pageSize = 10;
    private int pageIndex = 0;

    //On create method of the Tournament Activity calling and setting up functions and variables
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        context = this.getApplicationContext();

        setMainTournamentHeaderLayout();
        buildTournamentSummaryList(context);
        listMoreTournaments();
        setNavigationBar();
    }

    //Sets up and builds the Header of the Activity
    public void setMainTournamentHeaderLayout() {
        LinearLayout headerContainer = (LinearLayout) findViewById(R.id.tournament_main_header);
        TournamentHeader header = new TournamentHeader(context);
        headerContainer.addView(header);
        header.getProfileButton().setOnClickListener(v -> context.startActivity(new Intent(context,
                MyProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

    //Passes a list of all tournaments from the server to the addTournaments() method
    private void listMoreTournaments() {
        TeamHandler.listTournaments(pageIndex, pageSize, tournaments -> this.addTournaments(tournaments));
    }

    //Adds the tournaments from the given list to the global tournaments list
    private void addTournaments(List<TournamentModel> tournaments) {
        tournamentsList.addTournaments(tournaments);
    }

    //Builds the tournament list component
    private void buildTournamentSummaryList(Context context) {
        tournamentsList = new TournamentSummaryList(context, new ArrayList<>());
        tournamentsLayout = (LinearLayout) findViewById(R.id.tournament_list);
        tournamentsLayout.addView(tournamentsList);
    }

    //Sets up the navigation bar at the bottom of the Activity
    public void setNavigationBar() {
        BottomNavigationView bottomNav = findViewById(R.id.tournament_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_tournament);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    break;
                case R.id.navigation_discovery:
                    intent = new Intent(this, DiscoveryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
                case R.id.navigation_schedule:
                    intent = new Intent(this, ScheduleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
            }

            return true;
        });
    }
}
