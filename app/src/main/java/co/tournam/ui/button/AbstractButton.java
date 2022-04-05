package co.tournam.ui.button;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractButton extends LinearLayout {

    public AbstractButton(Context context) {
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}