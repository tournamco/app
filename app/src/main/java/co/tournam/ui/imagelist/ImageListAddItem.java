package co.tournam.ui.imagelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class ImageListAddItem extends LinearLayout {

    private ImageButton button;

    /**
     * The constructor for ImageListAddItem.
     *
     * @param context the current context
     */
    public ImageListAddItem(Context context) {
        super(context);
        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

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
        inflater.inflate(R.layout.imagelist_add_base, this, true);

        button = (ImageButton) findViewById(R.id.imagelist_add_button);
    }

    /**
     * Getter element for a button.
     *
     * @return the button element
     */
    public ImageButton getButton() {
        return button;
    }
}
