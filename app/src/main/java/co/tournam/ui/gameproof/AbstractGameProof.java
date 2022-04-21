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

    /**
     * Constructor for AbstractGameProof
     *
     * @param context the current context
     * @param game    the gameModel
     * @param match   the match being visualized
     */
    public AbstractGameProof(Context context, GameModel game, MatchModel match) {
        super(context);

        this.game = game;
        this.match = match;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}


