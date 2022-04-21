package co.tournam.ui.Slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class Slider extends LinearLayout {
    public Button buttonLocal;
    public Button buttonOnline;
    private boolean localBool;

    /**
     * The constructor for Slider.
     *
     * @param context      the current context
     * @param newLocalBool the boolean that declares whether local or global is selected
     */
    public Slider(Context context, boolean newLocalBool) {
        super(context);

        this.localBool = newLocalBool;

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
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

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
        inflater.inflate(R.layout.sample_ui_slider, this, true);

        buttonLocal = (Button) findViewById(R.id.firstButton);
        buttonOnline = (Button) findViewById(R.id.secondButton);

        setButtons(localBool);
    }

    /**
     * A setter for the locality of the slider.
     *
     * @param localBool the boolean that declares whether a tournament is local or global
     */
    public void setButtons(boolean localBool) {
        if (localBool == true) {
            buttonOnline.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonLocal.setBackgroundTintList(null);
        } else {
            buttonLocal.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonOnline.setBackgroundTintList(null);
        }
        this.localBool = localBool;
    }

    /**
     * a getter for the boolean.
     *
     * @return the boolean that declares whether a tournament is local or global
     */
    public boolean getBool() {
        return this.localBool;
    }


}
