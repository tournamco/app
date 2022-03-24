package co.tournam.schedule;

import android.content.Context;
import android.widget.LinearLayout;

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

        // TODO: GET MATCHLIST FROM SERVER
        List<MatchModel> fake = new ArrayList<MatchModel>();

        headerDefault header = new headerDefault(this.getContext(), "Schedule");

        Slider slider = new Slider(this.getContext(), false);

        FilterDropdown filter = new FilterDropdown(this.getContext());

        SummaryMatchList matchList = new SummaryMatchList(this.getContext(), fake);

        this.addView(header);
        this.addView(slider);
        this.addView(filter);
        this.addView(matchList);
    }
}
