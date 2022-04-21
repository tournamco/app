package co.tournam.ui.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class SubtextTitle extends AbstractTitle {
    private TextView titleText;
    private String subtext;

    /**
     * The constructor for the SubTextTitle class.
     *
     * @param name    the name of the title
     * @param subtext the subtext of the title
     * @param context the current context.
     */
    public SubtextTitle(String name, String subtext, Context context) {
        super(name, context);

        this.subtext = subtext;

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
        inflater.inflate(R.layout.subtext_title, this, true);

        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText(getName());

        TextView subtext = (TextView) findViewById(R.id.subtitle_text);
        subtext.setText(this.subtext);
    }

}
