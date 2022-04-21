package co.tournam.ui.filterdropdown;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractFilterDropdown extends LinearLayout {

    /**
     * Constructor for AbstractFilterDropdown
     *
     * @param context the current context
     */
    public AbstractFilterDropdown(Context context) {
        super(context);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}