package co.tournam.ui.imagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class ImageListItem extends LinearLayout {

    private Bitmap image;
    private ImageView mainView;

    public ImageListItem(Context context, Bitmap image) {
        super(context);

        this.image = image;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.imagelist_base, this, true);

        mainView = findViewById(R.id.imagelist_base);
        mainView.setImageBitmap(image);
    }
}
