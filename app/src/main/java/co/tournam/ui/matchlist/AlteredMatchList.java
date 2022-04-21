package co.tournam.ui.matchlist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.MatchModel;

public class AlteredMatchList extends AbstractMatchList {

    /**
     * The constructor for AlteredMatchList
     *
     * @param context the current context
     * @param matches the list of matches
     */
    public AlteredMatchList(Context context, List<MatchModel> matches) {
        super(context, matches);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {

        int i = 1;
        for (MatchModel match : this.matches) {
            AlteredMatchListHeader header = new AlteredMatchListHeader(context, i);
            AlteredMatchListItem item = new AlteredMatchListItem(context, match);
            this.addView(header);
            this.addView(item);
            i++;
        }
    }
}
