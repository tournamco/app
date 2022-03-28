package co.tournam.ui.imagelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class ImageListAddItem extends LinearLayout {

    public ImageListAddItem(Context context) {
        super(context);
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.imagelist_add_base, this, true);
    }
}