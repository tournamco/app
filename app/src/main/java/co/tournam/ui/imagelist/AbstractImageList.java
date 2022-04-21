package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import java.util.List;

public abstract class AbstractImageList extends LinearLayout {

    protected List<Bitmap> images;
    protected Context context;

    /**
     * The Constructor of the AbstractImageList
     *
     * @param context the current context
     * @param images  the images for the imageList
     */
    public AbstractImageList(Context context, List<Bitmap> images) {
        super(context);

        this.images = images;
        this.context = context;

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
    }
}