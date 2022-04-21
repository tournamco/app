package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import java.util.List;

public class ImageListVertical extends AbstractImageList {

    /**
     * Constructor for the ImageListVertical class
     *
     * @param context the current context
     * @param images  the list of images for the imagelist
     */
    public ImageListVertical(Context context, List<Bitmap> images) {

        super(context, images);

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
        for (Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            this.addView(item);
        }
    }
}
