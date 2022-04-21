package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import java.util.List;

public class ImageListHorizontal extends AbstractImageList {

    public ImageListHorizontal(Context context, List<Bitmap> images) {
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
        for (Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            this.addView(item);
        }
    }

    /**
     * A function to add an image to the imageList.
     *
     * @param image the image being added to the imageList
     */
    public void addImage(Bitmap image) {
        ImageListItem item = new ImageListItem(context, image);

        this.addView(item);
    }
}
