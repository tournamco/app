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

    /**
     * The constructor for the ImageListItem class.
     *
     * @param context the current context
     * @param image   the input image
     */
    public ImageListItem(Context context, Bitmap image) {
        super(context);

        this.image = image;

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
        inflater.inflate(R.layout.imagelist_base, this, true);

        mainView = findViewById(R.id.imagelist_base);

        if (image != null) {
            mainView.setImageBitmap(image);
        }
    }

    /**
     * setter for the ImageListItem class
     *
     * @param image The image to set
     */
    public void setImage(Bitmap image) {
        mainView.setImageBitmap(image);
    }
}
