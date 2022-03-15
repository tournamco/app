package co.tournam.ui.matchlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.schedule.R;

public class SummaryMatchList extends AbstractMatchList {
    public SummaryMatchList(Context context, List<MatchModel> matches) {
        super(context, matches);

        build(context);
    }

    private void build(Context context) {
        buildContents(context);
    }

    private void buildContents(Context context) {
        for(MatchModel match : this.matches) {
            SummaryMatchListItem item = new SummaryMatchListItem(context, match);

            this.addView(item);
        }
    }
}
