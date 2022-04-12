package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class ScheduleHeader extends BigHeader {
    private TextView textview;

    public ScheduleHeader(Context context) {
        super(context, "Schedule");

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_schedule_header, this, true);

        this.textview = (TextView) findViewById(R.id.title_schedule);
        setTitle(getName());
    }

    public void setTitle(String title) {
        this.textview.setText(title);
    }
}
