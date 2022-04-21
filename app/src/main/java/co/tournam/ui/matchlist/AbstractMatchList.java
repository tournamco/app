package co.tournam.ui.matchlist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.MatchModel;

public abstract class AbstractMatchList extends LinearLayout {

    protected List<MatchModel> matches;

    /**
     * @param context the current context.
     * @param matches the list of matches in the list.
     */
    public AbstractMatchList(Context context, List<MatchModel> matches) {
        super(context);

        this.matches = matches;

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
    }
}
