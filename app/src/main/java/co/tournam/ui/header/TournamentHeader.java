package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class TournamentHeader extends BigHeader {

    private TextView title;
    private Button profileButton;

    /**
     * Constructor for TournamentHeader.
     *
     * @param context the current context.
     */
    public TournamentHeader(Context context) {
        super(context, "Tournaments");

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * buildContents inflates the tournament header and sets the title and profile.
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_tournament_header, this, true);

        title = (TextView) findViewById(R.id.title_tournament_header);
        title.setText(getName());

        profileButton = (Button) findViewById(R.id.button_profile_tournament);
    }

    /**
     * getter function for the profile button
     *
     * @return the profile button.
     */
    public Button getProfileButton() {
        return profileButton;
    }
}
