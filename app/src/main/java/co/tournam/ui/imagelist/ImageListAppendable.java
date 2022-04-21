package co.tournam.ui.imagelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import co.tournam.api.UploadImageWorker;

public class ImageListAppendable extends AbstractImageList {
    private LinearLayout sublist;
    private ImageAppendListener listener;
    private int index;

    /**
     * The constructor for ImageListAppendable.
     *
     * @param context  the current context.
     * @param index    the index.
     * @param images   the list of images.
     * @param listener the listener being used.
     */
    public ImageListAppendable(Context context, int index, List<Bitmap> images, ImageAppendListener listener) {
        super(context, images);

        this.listener = listener;
        this.index = index;

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
        sublist = new LinearLayout(context);
        sublist.setOrientation(LinearLayout.HORIZONTAL);
        sublist.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.addView(sublist);

        for (Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            sublist.addView(item);
        }

        ImageListAddItem addItem = new ImageListAddItem(context);
        addItem.getButton().setOnClickListener(v -> {
            listener.onImageAppend(index);
        });
        this.addView(addItem);
    }

    /**
     * Function to add an image to the imageList
     *
     * @param proofImage the image used as proof
     */
    public void addImage(Bitmap proofImage) {
        ImageListItem item = new ImageListItem(this.getContext(), proofImage);

        sublist.addView(item);
    }

    /**
     * Listener for this class.
     */
    public interface ImageAppendListener {
        void onImageAppend(int index);
    }
}