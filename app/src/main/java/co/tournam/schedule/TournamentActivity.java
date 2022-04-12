package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerDiscovery;
import co.tournam.ui.header.headerTournament;
import co.tournam.ui.tournament_summary.TournamentSummaryList;

public class TournamentActivity extends AppCompatActivity {

    private Context context;
    private FilterDropdown filter;
    private Slider slider;
    private DefaultButtonFilled button;
    private LinearLayout tournamentsLayout;
    private TournamentSummaryList tournamentsList;
    private final int pageSize = 10;
    private int pageIndex = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        context = this.getApplicationContext();

        setMainTournamentHeaderLayout();
        buildTournamentSummaryList(context);
        listMoreTournaments();
        setNavigationBar();
    }

    public void setMainTournamentHeaderLayout() {
        LinearLayout headerContainer = (LinearLayout) findViewById(R.id.tournament_main_header);
        headerTournament header = new headerTournament(context, "Tournament");
        headerContainer.addView(header);
    }

    private void listMoreTournaments() {
        TeamHandler.listTournaments(pageIndex, pageSize, new TeamHandler.ListTournamentsComplete() {
            @Override
            public void success(List<TournamentModel> tournaments) {
                TournamentActivity.this.addTournaments(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void addTournaments(List<TournamentModel> tournaments) {
        tournamentsList.addTournaments(tournaments);
    }

    private void buildTournamentSummaryList(Context context) {
        tournamentsList = new TournamentSummaryList(context, new ArrayList<>());
        tournamentsLayout = (LinearLayout) findViewById(R.id.tournament_list);
        tournamentsLayout.addView(tournamentsList);
    }

    public void setNavigationBar() {
        BottomNavigationView bottomNav = findViewById(R.id.tournament_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_tournament);
        bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    startActivity(new Intent(TournamentActivity.this, TournamentActivity.class));
                    break;
                case R.id.navigation_discovery:
                    startActivity(new Intent(TournamentActivity.this, DiscoveryActivity.class));
                    break;
                case R.id.navigation_schedule:
                    startActivity(new Intent(TournamentActivity.this, ScheduleActivity.class));
                    break;
            }

            return true;
        });
    }
}
