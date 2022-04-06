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
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.SliderSchedule;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerSchedule;
import co.tournam.ui.matchlist.SummaryMatchList;

public class ScheduleActivity extends AppCompatActivity {

    Context context;
    private LinearLayout mainHeaderLayout;
    private LinearLayout sliderLayout;
    private LinearLayout filterDropdownLayout;
    private LinearLayout matchListLayout;
    private List<MatchModel> allMatchList;

    private boolean isPersonal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        this.context = this.getApplicationContext();

        setMainHeaderLayout();
        setSliderLayout();
        setFilterDropdownLayout();
        setMatchListLayout();
        setNavigationBar();

    }

    public void setMainHeaderLayout() {

        mainHeaderLayout = (LinearLayout) findViewById(R.id.schedule_main_header);
        headerSchedule header = new headerSchedule(context, "Schedule");
        mainHeaderLayout.addView(header);
    }

    public void setSliderLayout() {

        sliderLayout = (LinearLayout) findViewById(R.id.schedule_slider);
        SliderSchedule slider = new SliderSchedule(context, false);
        sliderLayout.addView(slider);

        slider.buttonAll.setOnClickListener(v -> {
            slider.setButtons(false);
            this.isPersonal = slider.getBool();
        });

        slider.buttonPersonal.setOnClickListener(v -> {
            slider.setButtons(true);
            this.isPersonal = slider.getBool();
        });

        this.isPersonal = slider.getBool();
    }

    public void setFilterDropdownLayout() {
        //TODO Set up Filter Dropdown
        filterDropdownLayout = (LinearLayout) findViewById(R.id.schedule_filter_dropdown);
        FilterDropdown filter = new FilterDropdown(context);
        filterDropdownLayout.addView(filter);

    }

    public void setMatchListLayout() {

        matchListLayout = (LinearLayout) findViewById(R.id.schedule_match_list);


        if (this.isPersonal) {

            TeamHandler.listMatches(0, 10, new TeamHandler.ListMatchesComplete() {
                @Override
                public void success(List<MatchModel> matches) {
                    SummaryMatchList matchList  = new SummaryMatchList(context, matches);
                    matchListLayout.addView(matchList);
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });

        } else {
            allMatchList = new ArrayList<>();
            TeamHandler.listTournaments(0, 10, new TeamHandler.ListTournamentsComplete() {
                @Override
                public void success(List<TournamentModel> tournaments) {
                    for (TournamentModel tournament : tournaments) {
                        TournamentHandler.listMatches(tournament.getId(), new TournamentHandler.ListMatchesComplete() {
                            @Override
                            public void success(List<MatchModel> matches) {
                                allMatchList.addAll(matches);
                            }

                            @Override
                            public void failure(ApiErrors error, String message) {
                                System.err.println("API_ERROR: " + error.name() + " - " + message);
                            }
                        });
                    }
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
            SummaryMatchList matchList  = new SummaryMatchList(context, allMatchList);
            matchListLayout.addView(matchList);

        }
    }

    public void setNavigationBar() {

        BottomNavigationView bottomNav = findViewById(R.id.schedule_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_schedule);
        bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    startActivity(new Intent(ScheduleActivity.this, TournamentActivity.class));
                    break;
                case R.id.navigation_discovery:
                    startActivity(new Intent(ScheduleActivity.this, DiscoveryActivity.class));
                    break;
                case R.id.navigation_schedule:
                    startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
                    break;
            }

            return true;
        });
    }
}