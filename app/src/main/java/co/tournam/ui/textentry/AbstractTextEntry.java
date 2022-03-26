package co.tournam.ui.textentry;

import android.content.Context;
import android.widget.LinearLayout;

public class AbstractTextEntry extends LinearLayout {

    public AbstractTextEntry(Context context) {
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}
