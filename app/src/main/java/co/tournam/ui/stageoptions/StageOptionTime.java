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
//    private boolean thresholdType;
//
    private EditText fromEdit;
    private EditText toEdit;


    public StageOptionTime(Context context, String title, int type) {
        super(context);

        this.title = title;
        this.type = type;
//        this.thresholdType = thresholdType;
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

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

    public String getFirstEntry() { return this.fromEdit.getText().toString(); }

    public String getSecondEntry() { return this.toEdit.getText().toString(); }

    public void setHints(String hint) {
        fromEdit.setHint(hint);
        toEdit.setHint(hint);
    }
}