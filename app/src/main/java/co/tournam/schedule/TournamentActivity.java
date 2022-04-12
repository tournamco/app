package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.tournament_summary.TournamentSummaryList;

public class TournamentActivity extends AppCompatActivity {

    private Context context;
    private FilterDropdown filter;
    private Slider slider;
    private DefaultButtonFilled button;
    private LinearLayout tournamentsLayout;
    private final int pageSize = 10;
    private int pageIndex = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        context = this.getApplicationContext();


        setNavigationBar();
    }

    private void listMoreTournaments() {
        TeamHandler.listTournaments(pageIndex, pageSize, new TeamHandler.ListTournamentsComplete() {
            @Override
            public void success(List<TournamentModel> matches) {
                TournamentActivity.this.buildTournamentSummaryList(matches, context);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void buildTournamentSummaryList(List<TournamentModel> tournaments, Context context) {
        TournamentSummaryList list = new TournamentSummaryList(context, tournaments);
        tournamentsLayout = (LinearLayout) findViewById(R.id.tournamentsListing);
        tournamentsLayout.addView(list);
    }

    public void setNavigationBar() {

        BottomNavigationView bottomNav = findViewById(R.id.discovery_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_discovery);
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
