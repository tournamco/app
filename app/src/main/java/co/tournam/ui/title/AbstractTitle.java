package co.tournam.ui.title;

import android.content.Context;
import android.widget.LinearLayout;

public class AbstractTitle extends LinearLayout {

    private String name;

    /**
     * The constructor for AbstractTitle.
     *
     * @param name    the name of the title
     * @param context the current context
     */
    public AbstractTitle(String name, Context context) {
        super(context);
        this.name = name;
    }

    /**
     * the getter for the name of the title
     *
     * @return the name of the title.
     */
    public String getName() {
        return name;
    }
}
