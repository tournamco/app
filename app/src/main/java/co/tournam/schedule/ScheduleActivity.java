package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.models.MatchModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerSchedule;
import co.tournam.ui.matchlist.SummaryMatchList;

public class ScheduleActivity extends AppCompatActivity {

    Context context;
    private LinearLayout mainHeaderLayout;
    private LinearLayout sliderLayout;
    private LinearLayout filterDropdownLayout;
    private LinearLayout matchListLayout;

    private boolean isPersonal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        this.context = this.getApplicationContext();

        setMainHeaderLayout();
        setSliderLayout();
        setFilterDropdownLayout();
        setMatchListLayout();

    }

    public void setMainHeaderLayout() {

        mainHeaderLayout = (LinearLayout) findViewById(R.id.schedule_main_header);
        headerSchedule header = new headerSchedule(context, "Schedule");
        mainHeaderLayout.addView(header);
    }

    public void setSliderLayout() {

        sliderLayout = (LinearLayout) findViewById(R.id.schedule_slider);
        Slider slider = new Slider(context, false);
        sliderLayout.addView(slider);
    }

    public void setFilterDropdownLayout() {
        //TODO Set up Filter Dropdown
        filterDropdownLayout = (LinearLayout) findViewById(R.id.schedule_filter_dropdown);
        FilterDropdown filter = new FilterDropdown(context);
        filterDropdownLayout.addView(filter);

    }

    public void setMatchListLayout() {

        matchListLayout = (LinearLayout) findViewById(R.id.schedule_match_list);


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

    }




}








//        RelativeLayout layout = new RelativeLayout(this.getContext());
//
//        // TODO: GET MATCHLIST FROM SERVER
//        List<MatchModel> fake = new ArrayList<MatchModel>();
//
//        Button button = new Button(this.getContext());
//
//        Slider slider = new Slider(this.getContext(), false);
//
//        FilterDropdown filter = new FilterDropdown(this.getContext());
//
//        headerDefault header = new headerDefault(this.getContext(), "Schedule");
//
//        SummaryMatchList matchList = new SummaryMatchList(this.getContext(), fake);
//
//
//
//        View main = getRootView();
//       // attachViewToParent(header, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        //attachViewToParent(slider, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//       // attachViewToParent(filter, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        //this.addView(layout);
//        this.addView(header);
//        this.addView(slider, 1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        this.addView(filter,1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        //this.addView(matchList);