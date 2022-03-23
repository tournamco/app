package co.tournam.ui.filterdropdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class FilterDropdown extends AbstractFilterDropdown {


    public FilterDropdown(Context context) {
        super(context);

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.filter_dropdown, this, true);


    }


}
