package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.ui.imagelist.AbstractImageList;

public class AbstractGameProof extends LinearLayout {

    private GameModel game;
    private MatchModel match;

    public AbstractGameProof(Context context, GameModel game, MatchModel match) {
        super(context);

        this.game = game;
        this.match = match;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}


