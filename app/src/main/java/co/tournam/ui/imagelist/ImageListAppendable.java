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

    public ImageListAppendable(Context context, int index, List<Bitmap> images, ImageAppendListener listener) {
        super(context, images);

        this.listener = listener;
        this.index = index;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        sublist = new LinearLayout(context);
        sublist.setOrientation(LinearLayout.HORIZONTAL);
        sublist.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.addView(sublist);

        for(Bitmap image : this.images) {
            ImageListItem item = new ImageListItem(context, image);

            sublist.addView(item);
        }

        ImageListAddItem addItem = new ImageListAddItem(context);
        addItem.getButton().setOnClickListener(v -> {
            listener.onImageAppend(index);
        });
        this.addView(addItem);
    }

    public void addImage(Bitmap proofImage) {
        ImageListItem item = new ImageListItem(this.getContext(), proofImage);

        sublist.addView(item);
    }

    public interface ImageAppendListener {
        void onImageAppend(int index);
    }
}