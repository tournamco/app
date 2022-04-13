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

    public StageOptionColorPicker(Context context) {
        super(context);
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.color_picker, this, true);

        titleText = findViewById(R.id.color_picker_title);
        titleText.setText("Color");

        colorButton = findViewById(R.id.color_display);

    }

    public void setColor(int color) {
        colorButton.setBackgroundColor(color);
    }


//    public String getEntry() {
//        return this.entryText.getText().toString();
//    }
}