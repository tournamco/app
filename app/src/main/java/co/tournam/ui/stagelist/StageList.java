package co.tournam.ui.stagelist;

import android.content.Context;

import java.util.ArrayList;

import co.tournam.models.stage.StageModel;


public class StageList extends AbstractStageList {
    public StageList(Context context, ArrayList<StageModel> stages) {
        super(context, stages);

        build(context);
    }

    public void build(Context context) {


        buildContents(context);
    }

    public void buildContents(Context context) {
        for (StageModel stage : this.stages) {
            StageListItem item = new StageListItem(context, stage);
            this.addView(item);
        }

    }

}
