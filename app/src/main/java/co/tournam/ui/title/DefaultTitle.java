package co.tournam.ui.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class DefaultTitle extends AbstractTitle {
    private TextView titleText;

    /**
     * Constructor for the DefaultTitle class.
     *
     * @param name    the name of the title
     * @param context the current context
     */
    public DefaultTitle(String name, Context context) {
        super(name, context);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.default_title, this, true);

        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText(getName());
    }
}
