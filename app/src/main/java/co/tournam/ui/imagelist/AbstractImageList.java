package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import java.util.List;

public abstract class AbstractImageList extends LinearLayout {

    protected List<Bitmap> images;
    protected Context context;

    public AbstractImageList(Context context, List<Bitmap> images) {
        super(context);

        this.images = images;
        this.context = context;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}