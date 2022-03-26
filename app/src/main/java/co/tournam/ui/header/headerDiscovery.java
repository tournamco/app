package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class headerDiscovery extends LinearLayout {

    private TextView title;
    private String headerText;
    private Button button;



    public headerDiscovery(Context context, String headerText) {
        super(context);

        this.headerText = headerText;


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
        inflater.inflate(R.layout.sample_big_header, this, true);

        title = (TextView) findViewById(R.id.title);
        title.setText(headerText);

        button = (Button) findViewById(R.id.button_create);


    }
}
