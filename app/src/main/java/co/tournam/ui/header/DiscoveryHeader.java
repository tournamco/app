package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class DiscoveryHeader extends BigHeader {
    private TextView title;
    public Button button;

    /**
     * The constructor for the DiscoveryHeader class.
     *
     * @param context the current context
     */
    public DiscoveryHeader(Context context) {
        super(context, "Discovery");

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
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * BuildContents inflates the header UI element and sets the name of the button
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_discovery_header, this, true);

        title = (TextView) findViewById(R.id.title);
        title.setText(getName());

        button = (Button) findViewById(R.id.button_create);
    }

    /**
     * Can be used to access the button UI element.
     *
     * @return the button element
     */
    public View getButton() {
        return button;
    }
}
