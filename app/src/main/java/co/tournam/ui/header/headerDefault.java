package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.TeamModel;
import co.tournam.schedule.R;


public class headerDefault extends LinearLayout{

    private TextView title;
    private String headerText;
    private TextView useless;


    public headerDefault(Context context, String headerText) {
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
        inflater.inflate(R.layout.header, this, true);

        title = (TextView) findViewById(R.id.teamsOrMembers);
        title.setText(headerText);

        useless = findViewById(R.id.memberNumber);
        useless.setText("");
    }
}
