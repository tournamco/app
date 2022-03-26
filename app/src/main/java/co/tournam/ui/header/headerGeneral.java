package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import co.tournam.schedule.R;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.filterdropdown.FilterDropdown;

public class headerGeneral extends ConstraintLayout {

    private TextView titleText;
    private String headerText;
    private Slider slider;
    private FilterDropdown filter;
    private DefaultButtonFilled buttonAbstract;



    public headerGeneral(Context context, String headerText, Slider slider, FilterDropdown filter, DefaultButtonFilled buttonAbstract) {
        super(context);

        this.slider = slider;
        this.filter = filter;
        this.buttonAbstract = buttonAbstract;
        this.headerText = headerText;

        build(context);
    }

    private void build(Context context) {

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.general_header, this, true);

        titleText = (TextView) findViewById(R.id.title);
        titleText.setText(headerText);




        filter = (FilterDropdown) findViewById(R.id.filter);
        slider.setVisibility(VISIBLE);

        slider = (Slider) findViewById(R.id.slider);
        slider.setVisibility(VISIBLE);

    }

    public void setVisible(View view, boolean input){
        if(input == true){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

}
