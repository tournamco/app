package co.tournam.ui.stageoptions;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.util.Pools;

import java.util.List;

import co.tournam.models.stage.PoolsStageModel;

public abstract class AbstractStageOptions extends LinearLayout {

    protected PoolsStageModel stage;

    public AbstractStageOptions(Context context, PoolsStageModel stage) {
        super(context);

        this.stage = stage;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
