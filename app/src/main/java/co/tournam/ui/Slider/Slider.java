package co.tournam.ui.Slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class Slider extends LinearLayout{
    private Button buttonLocal;
    private Button buttonOnline;
    private boolean localBool;

    public Slider(Context context, boolean newLocalBool)
    {
        super(context);

        this.localBool = newLocalBool;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_ui_slider, this, true);

        buttonLocal = (Button) findViewById(R.id.firstButton);
        buttonOnline = (Button) findViewById(R.id.secondButton);

        setButtons(localBool);
    }

    private void setButtons(boolean localBool) {
        if(localBool == true) {
            buttonOnline.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonLocal.setBackgroundTintList(null);
        }
        else {
            buttonLocal.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonOnline.setBackgroundTintList(null);
        }
    }
}
