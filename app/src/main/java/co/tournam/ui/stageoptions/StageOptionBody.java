package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class StageOptionBody extends LinearLayout {

    private TextView titleText;
    private String title;
    public EditText entryText;
    private int type;

    /**
     * The constructor for StageOptionBody
     *
     * @param context the current context
     * @param title   the title of the stage
     * @param type    the type of stage
     */
    public StageOptionBody(Context context, String title, int type) {
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
        inflater.inflate(R.layout.textentry_with_title, this, true);

        titleText = findViewById(R.id.textentry_title);
        titleText.setText(this.title);

        entryText = (EditText) findViewById(R.id.editTextEntry);
        entryText.setInputType(this.type);

    }

    /**
     * The function used to obtain the entry text
     *
     * @return the entry text.
     */
    public String getEntry() {
        return this.entryText.getText().toString();
    }

    /**
     * The setter for the entry text
     *
     * @param text the entry text to set to
     */
    public void setEntryText(String text) {
        entryText.setText(text);
    }
}