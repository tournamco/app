package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.ui.imagelist.AbstractImageList;

public class AbstractGameProof extends LinearLayout {

    private List<Bitmap> images;
    private MatchModel match;

    public AbstractGameProof(Context context, List<Bitmap> images, MatchModel match) {
        super(context);

        this.images = images;
        this.match = match;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}


