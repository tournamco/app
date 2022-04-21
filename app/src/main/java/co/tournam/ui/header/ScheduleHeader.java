package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class ScheduleHeader extends BigHeader {
    private TextView textview;

    /**
     * The constructor for the ScheduleHeader class.
     *
     * @param context the current context
     */
    public ScheduleHeader(Context context) {
        super(context, "Schedule");

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * The buildContents method inflates the big schedule header and sets the
     * title to the correct string.
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_schedule_header, this, true);

        this.textview = (TextView) findViewById(R.id.title_schedule);
        setTitle(getName());
    }

    /**
     * Sets the title of the header.
     *
     * @param title the input title
     */
    public void setTitle(String title) {
        this.textview.setText(title);
    }
}
