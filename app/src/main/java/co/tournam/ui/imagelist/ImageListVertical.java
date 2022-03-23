package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import java.util.List;

public class ImageListVertical extends AbstractImageList {

    public ImageListVertical(Context context, List<Bitmap> images) {

        super(context, images);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        for(Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            this.addView(item);
        }
    }
}
