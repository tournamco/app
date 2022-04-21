package co.tournam.ui.textentry;

import android.content.Context;
import android.widget.LinearLayout;

public class AbstractTextEntry extends LinearLayout {

    /**
     * The constructor for AbstractTextEntry
     *
     * @param context the current context
     */
    public AbstractTextEntry(Context context) {
        super(context);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}
