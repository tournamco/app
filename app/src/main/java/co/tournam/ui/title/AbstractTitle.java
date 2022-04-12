package co.tournam.ui.title;

import android.content.Context;
import android.widget.LinearLayout;

public class AbstractTitle extends LinearLayout {

    private String name;

    public AbstractTitle(String name, Context context) {
        super(context);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
