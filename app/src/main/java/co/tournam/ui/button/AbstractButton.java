package co.tournam.ui.button;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.util.Pools;

import java.util.List;

import co.tournam.models.stage.PoolsStageModel;

public abstract class AbstractButton extends LinearLayout {

    public AbstractButton(Context context) {
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}