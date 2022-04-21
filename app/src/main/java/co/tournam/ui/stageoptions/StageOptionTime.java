package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class StageOptionTime extends LinearLayout {

    private TextView titleText;
    private String title;
    private int type;

    private EditText fromEdit;
    private EditText toEdit;

    /**
     * Constructor for StageOptionTime.
     *
     * @param context the current context
     * @param title   the title of the stage
     * @param type    the type of the stage
     */
    public StageOptionTime(Context context, String title, int type) {
        super(context);

        this.title = title;
        this.type = type;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textentry_threshold, this, true);

        titleText = findViewById(R.id.textentry_threshold_title);
        titleText.setText(this.title);

        fromEdit = findViewById(R.id.textentry_threshold_from);
        fromEdit.setInputType(type);
        toEdit = findViewById(R.id.textentry_threshold_to);
        toEdit.setInputType(type);

    }

    /**
     * Get the first entry of the stage
     *
     * @return the first entry of the stage
     */
    public String getFirstEntry() {
        return this.fromEdit.getText().toString();
    }

    /**
     * Get the second entry of the stage
     *
     * @return the second entry of the stage
     */
    public String getSecondEntry() {
        return this.toEdit.getText().toString();
    }

    /**
     * Setter for the hints
     *
     * @param hint the hint that needs to be set.
     */
    public void setHints(String hint) {
        fromEdit.setHint(hint);
        toEdit.setHint(hint);
    }
}