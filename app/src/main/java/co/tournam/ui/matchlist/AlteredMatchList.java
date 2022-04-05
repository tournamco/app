package co.tournam.ui.matchlist;

import android.content.Context;

import java.util.List;

import co.tournam.models.MatchModel;

public class AlteredMatchList extends AbstractMatchList {

    public AlteredMatchList(Context context, List<MatchModel> matches) {
        super(context, matches);

        build(context);
    }

    private void build(Context context) {
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
