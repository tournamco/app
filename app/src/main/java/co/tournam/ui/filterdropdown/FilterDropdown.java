package co.tournam.ui.filterdropdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import co.tournam.schedule.R;

public class FilterDropdown extends AbstractFilterDropdown {

    private Spinner spinner;
    private TextView text;

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

        spinner = findViewById(R.id.filter_dropdown_spinner);
        text = findViewById(R.id.filter_dropdown_text);
        text.setText("Filter");


    }


}
