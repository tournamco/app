package co.tournam.ui.matchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class AlteredMatchListHeader extends LinearLayout {


    private int matchIndex;
    private TextView matchHeader;

    public AlteredMatchListHeader(Context context, int matchIndex) {
        super(context);

        this.matchIndex = matchIndex;

        build(context);
    }

    public void build(Context context) {
        setOrientation(HORIZONTAL);
        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.match_list_header_altered, this, true);

        matchHeader = (TextView) findViewById(R.id.match_index_header_altered);
        setMatchHeader(matchIndex);
    }

    public void setMatchHeader(int i) {
        matchHeader.setText("Match " + String.valueOf(i));
    }


}
