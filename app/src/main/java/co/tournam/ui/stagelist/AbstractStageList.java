package co.tournam.ui.stagelist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.stage.StageModel;

public abstract class AbstractStageList extends LinearLayout {

    protected List<StageModel> stages;

    public AbstractStageList(Context context, List<StageModel> stages) {
        super(context);

        this.stages = stages;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
