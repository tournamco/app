package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class StageOptionColorPicker extends LinearLayout {

    private TextView titleText;
    public ImageButton colorButton;

    /**
     * Constructor for StageOptionColorPicker.
     *
     * @param context the current context.
     */
    public StageOptionColorPicker(Context context) {
        super(context);
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
        inflater.inflate(R.layout.color_picker, this, true);

        titleText = findViewById(R.id.color_picker_title);
        titleText.setText("Color");

        colorButton = findViewById(R.id.color_display);

    }

    /**
     * Setter for the color of the background
     *
     * @param color the color to set the background to.
     */
    public void setColor(int color) {
        colorButton.setBackgroundColor(color);
    }
}