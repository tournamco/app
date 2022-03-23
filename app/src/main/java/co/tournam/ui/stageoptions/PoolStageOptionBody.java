package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import co.tournam.schedule.R;

public class PoolStageOptionBody extends LinearLayout {

    private TextView titleText;
    private String title;


    public PoolStageOptionBody(Context context, String title) {
        super(context);
        this.title = title;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textentry_with_title, this, true);

        titleText = findViewById(R.id.textentry_title);
        titleText.setText(this.title);

    }
}