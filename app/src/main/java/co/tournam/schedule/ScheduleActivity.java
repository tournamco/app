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
import co.tournam.models.MatchModel;
import co.tournam.ui.Slider.SliderSchedule;
import co.tournam.ui.header.ScheduleHeader;
import co.tournam.ui.matchlist.SummaryMatchList;

public class ScheduleActivity extends AppCompatActivity {

    //Variable Declarations
    Context context;
    private LinearLayout mainHeaderLayout;
    private LinearLayout selectSliderLayout;
    private LinearLayout timeSliderLayout;
    private LinearLayout matchListLayout;
    private SummaryMatchList matchList;
    private boolean isPersonal;
    private boolean future = true;

    //On create method of the Schedule Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        this.context = this.getApplicationContext();

        setMainHeaderLayout();
        setSelectSliderLayout();
        setTimeSliderLayout();
        setNavigationBar();

        matchListLayout = (LinearLayout) findViewById(R.id.schedule_match_list);
        matchList = new SummaryMatchList(context, new ArrayList<>());
        matchListLayout.addView(matchList);

        loadMatches();
    }

    //Sets and builds the header of the activity
    public void setMainHeaderLayout() {
        mainHeaderLayout = (LinearLayout) findViewById(R.id.schedule_main_header);
        ScheduleHeader header = new ScheduleHeader(context);
        mainHeaderLayout.addView(header);
    }

    //Sets and builds the Personal/All under the header of the activity
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

    //Sets and builds the Upcoming/Results under the header of the activity
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

    //Gets the matches for the user from the server and adds the to the global match list
    public void loadMatches() {
        TeamHandler.listMatches(this.future, this.isPersonal, 0, 10,
                matches -> matchList.addMatches(matches));
    }

    //Builds the navigation bar at the bottom of the activity
    public void setNavigationBar() {
        BottomNavigationView bottomNav = findViewById(R.id.schedule_bottomNav_view);
        bottomNav.setSelectedItemId(R.id.navigation_schedule);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    intent = new Intent(this, TournamentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
                case R.id.navigation_discovery:
                    intent = new Intent(this, DiscoveryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
                case R.id.navigation_schedule:
                    break;
            }

            return true;
        });
    }
}