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

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        for(Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            this.addView(item);
        }
    }

    public void addImage(Bitmap image) {
        ImageListItem item = new ImageListItem(context, image);

        this.addView(item);
    }
}
