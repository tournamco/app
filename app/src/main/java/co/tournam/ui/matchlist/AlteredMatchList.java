package co.tournam.ui.matchlist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.MatchModel;

public class AlteredMatchList extends AbstractMatchList {

    public AlteredMatchList(Context context, List<MatchModel> matches) {
        super(context, matches);

        build(context);
    }

    private void build(Context context) {
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    private void buildContents(Context context) {

        int i = 1;
        for(MatchModel match : this.matches) {
            AlteredMatchListHeader header = new AlteredMatchListHeader(context, i);
            AlteredMatchListItem item = new AlteredMatchListItem(context, match);
            this.addView(header);
            this.addView(item);
            i++;
        }
    }
}
