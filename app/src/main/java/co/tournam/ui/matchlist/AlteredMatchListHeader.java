package co.tournam.ui.matchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class AlteredMatchListHeader extends LinearLayout {


    private int matchIndex;
    private TextView matchHeader;

    /**
     * the constructor for the AlteredMatchListHeader.
     *
     * @param context    the current context
     * @param matchIndex the index of the match
     */
    public AlteredMatchListHeader(Context context, int matchIndex) {
        super(context);

        this.matchIndex = matchIndex;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        setOrientation(HORIZONTAL);
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.match_list_header_altered, this, true);

        matchHeader = (TextView) findViewById(R.id.match_index_header_altered);
        setMatchHeader(matchIndex);
    }

    /**
     * setter for the match header
     *
     * @param i the index of the match header.
     */
    public void setMatchHeader(int i) {
        matchHeader.setText("Match " + String.valueOf(i));
    }


}
