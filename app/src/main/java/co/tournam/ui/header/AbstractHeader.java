package co.tournam.ui.header;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractHeader extends LinearLayout {
    private String name;
    protected Context context;

    public AbstractHeader(Context context, String name) {
        super(context);
        this.context = context;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
