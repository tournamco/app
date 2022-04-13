package co.tournam.ui.Slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class SliderSchedule extends LinearLayout{
    public Button buttonPersonal;
    public Button buttonAll;
    private boolean personalBool;
    private String valueA;
    private String valueB;

    public SliderSchedule(Context context, boolean newLocalBool, String valueA, String valueB)
    {
        super(context);

        this.personalBool = newLocalBool;
        this.valueA = valueA;
        this.valueB = valueB;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_ui_slider_schedule, this, true);

        buttonPersonal = (Button) findViewById(R.id.firstButton_schedule);
        buttonPersonal.setText(valueA);
        buttonAll = (Button) findViewById(R.id.secondButton_schedule);
        buttonAll.setText(valueB);

        setButtons(personalBool);
    }

    public void setButtons(boolean bool) {
        if(bool == false) {
            buttonPersonal.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonAll.setBackgroundTintList(null);
        }
        else {
            buttonAll.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FF757575")));
            buttonPersonal.setBackgroundTintList(null);
        }
        this.personalBool = bool;
    }

    public boolean getBool() {
        return this.personalBool;
    }


}
