package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class headerTournament extends LinearLayout {

    private TextView title;
    private String headerText;
    public Button profileButton;



    public headerTournament(Context context, String headerText) {
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
        inflater.inflate(R.layout.tournament_header, this, true);

        title = (TextView) findViewById(R.id.title_tournament_header);
        title.setText(headerText);

        profileButton = (Button) findViewById(R.id.button_profile_tournament);
        profileButton.setText("Profile");


    }
}
