package co.tournam.ui.button;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

import co.tournam.schedule.R;

public class DefaultButton extends AbstractButton {

    private String title;
    public Button button;

    /**
     * Constructor for the DefaultButton Class
     *
     * @param context the current context
     * @param title   the title of the button
     */
    public DefaultButton(Context context, String title) {
        super(context);
        this.title = title;
        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        buildContents(context);
    }

    /**
     * buildContents class takes the context and uses it to inflate the layout create the button.
     *
     * @param context the current context
     */
    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_ui_button, this, true);

        button = findViewById(R.id.default_button);
        button.setText(title);
    }
}
