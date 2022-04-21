package co.tournam.ui.textentry;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class Text extends LinearLayout {

    private TextView textView;
    private String text;

    /**
     * The constructor for the Text Class
     *
     * @param context the current context
     * @param text    the text
     */
    public Text(Context context, String text) {
        super(context);

        this.text = text;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
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
        inflater.inflate(R.layout.sample_text, this, true);

        textView = (TextView) findViewById(R.id.defaultText);
        textView.setText(text);
    }
}
