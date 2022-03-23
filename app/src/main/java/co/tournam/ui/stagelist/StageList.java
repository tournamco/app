package co.tournam.ui.stagelist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.*;

import co.tournam.models.RoundModel;
import co.tournam.models.stage.PoolsStageModel;
import co.tournam.ui.roundbar.AbstractRoundBar;
import co.tournam.ui.roundbar.RoundBarItem;


public class StageList extends AbstractStageList {
    public StageList(Context context, ArrayList<PoolsStageModel> stages) {
        super(context, stages);

        build(context);
    }

    public void build(Context context) {


        buildContents(context);
    }

    public void buildContents(Context context) {
        for (PoolsStageModel stage : this.stages) {
            StageListItem item = new StageListItem(context, stage);
            this.addView(item);
        }

    }

}
