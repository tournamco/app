package co.tournam.ui.filterdropdown;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractFilterDropdown extends LinearLayout {

    public AbstractFilterDropdown(Context context) {
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}