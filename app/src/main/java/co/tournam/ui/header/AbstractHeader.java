package co.tournam.ui.header;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractHeader extends LinearLayout {
    private String name;
    protected Context context;

    /**
     * The constructor for AbstractHeader.
     *
     * @param context the current context
     * @param name    the name for the header
     */
    public AbstractHeader(Context context, String name) {
        super(context);
        this.context = context;
        this.name = name;
    }

    /**
     * returns the given name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Allows for someone to change the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
