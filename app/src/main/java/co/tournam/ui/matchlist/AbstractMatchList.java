package co.tournam.ui.matchlist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.MatchModel;

public abstract class AbstractMatchList extends LinearLayout {

    protected List<MatchModel> matches;

    public AbstractMatchList(Context context, List<MatchModel> matches) {
        super(context);

        this.matches = matches;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
