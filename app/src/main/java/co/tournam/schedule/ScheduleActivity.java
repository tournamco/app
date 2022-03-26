package co.tournam.schedule;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerDefault;
import co.tournam.ui.matchlist.SummaryMatchList;

public class ScheduleActivity extends LinearLayout {

    public ScheduleActivity(Context context) {

        super(context);

        build(context);
    }

    public void build(Context context) {

        setOrientation(VERTICAL);

        buildContents(context);
    }

    public void buildContents(Context context) {

        RelativeLayout layout = new RelativeLayout(this.getContext());

        // TODO: GET MATCHLIST FROM SERVER
        List<MatchModel> fake = new ArrayList<MatchModel>();

        Button button = new Button(this.getContext());

        Slider slider = new Slider(this.getContext(), false);

        FilterDropdown filter = new FilterDropdown(this.getContext());

        headerDefault header = new headerDefault(this.getContext(), "Schedule");

        SummaryMatchList matchList = new SummaryMatchList(this.getContext(), fake);



        View main = getRootView();
       // attachViewToParent(header, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //attachViewToParent(slider, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
       // attachViewToParent(filter, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //this.addView(layout);
        this.addView(header);
        this.addView(slider, 1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.addView(filter,1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //this.addView(matchList);
    }
}
