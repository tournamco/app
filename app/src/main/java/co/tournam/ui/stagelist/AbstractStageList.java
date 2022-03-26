package co.tournam.ui.stagelist;
import android.content.Context;
import android.service.autofill.FieldClassification;
import android.widget.LinearLayout;
import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.models.stage.PoolsStageModel;

public abstract class AbstractStageList extends LinearLayout {

    protected List<PoolsStageModel> stages;

    public AbstractStageList(Context context, List<PoolsStageModel> stages) {
        super(context);

        this.stages = stages;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
