package co.tournam.ui.button;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractButton extends LinearLayout {
    /**
     * @param context the current context
     * @desc Constructor for the AbstractButton class
     */
    public AbstractButton(Context context) {
        super(context);
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }

}