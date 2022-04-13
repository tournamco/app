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
import co.tournam.ui.header.ScheduleHeader;
import co.tournam.ui.matchlist.SummaryMatchList;

public class ScheduleActivity extends AppCompatActivity {

    Context context;
    private LinearLayout mainHeaderLayout;
    private LinearLayout selectSliderLayout;
    private LinearLayout timeSliderLayout;
    private LinearLayout filterDropdownLayout;
    private LinearLayout matchListLayout;
    private SummaryMatchList matchList;
    private List<MatchModel> allMatchList;

    private boolean isPersonal;
    private boolean future = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        this.context = this.getApplicationContext();

        setMainHeaderLayout();
        setSelectSliderLayout();
        setTimeSliderLayout();
        setFilterDropdownLayout();
        setNavigationBar();

        matchListLayout = (LinearLayout) findViewById(R.id.schedule_match_list);
        matchList = new SummaryMatchList(context, new ArrayList<>());
        matchListLayout.addView(matchList);

        loadMatches();
    }

    public void setMainHeaderLayout() {

        mainHeaderLayout = (LinearLayout) findViewById(R.id.schedule_main_header);
        ScheduleHeader header = new ScheduleHeader(context);
        mainHeaderLayout.addView(header);
    }

    public void setSelectSliderLayout() {
        selectSliderLayout = (LinearLayout) findViewById(R.id.schedule_slider);
        SliderSchedule slider = new SliderSchedule(context, false, "Personal", "All");
        selectSliderLayout.addView(slider);

        slider.buttonAll.setOnClickListener(v -> {
            slider.setButtons(false);
            this.isPersonal = slider.getBool();
            matchList.clear();
            loadMatches();
        });

        slider.buttonPersonal.setOnClickListener(v -> {
            slider.setButtons(true);
            this.isPersonal = slider.getBool();
            matchList.clear();
            loadMatches();
        });

        this.isPersonal = slider.getBool();
    }

    public void setTimeSliderLayout() {
        timeSliderLayout = (LinearLayout) findViewById(R.id.time_slider);
        SliderSchedule slider = new SliderSchedule(context, true, "Upcoming", "Results");
        timeSliderLayout.addView(slider);

        slider.buttonAll.setOnClickListener(v -> {
            slider.setButtons(false);
            this.future = slider.getBool();
            matchList.clear();
            loadMatches();
        });

        slider.buttonPersonal.setOnClickListener(v -> {
            slider.setButtons(true);
            this.future = slider.getBool();
            matchList.clear();
            loadMatches();
        });

        this.future = slider.getBool();
    }

    public void setFilterDropdownLayout() {
        //TODO Set up Filter Dropdown
        filterDropdownLayout = (LinearLayout) findViewById(R.id.schedule_filter_dropdown);
        FilterDropdown filter = new FilterDropdown(context);
        filterDropdownLayout.addView(filter);
    }

    public void loadMatches() {
        if (this.isPersonal) {
            // TODO: Get Personal Matches api call
        } else {
            TeamHandler.listMatches(this.future,0, 10, new TeamHandler.ListMatchesComplete() {
                @Override
                public void success(List<MatchModel> matches) {
                    matchList.addMatches(matches);
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
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