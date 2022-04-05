package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import java.util.List;

import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.ui.imagelist.AbstractImageList;
import co.tournam.ui.imagelist.ImageListAddItem;
import co.tournam.ui.imagelist.ImageListAppendable;
import co.tournam.ui.imagelist.ImageListItem;

public class GameProof extends AbstractGameProof {

    private GameModel game;
    private MatchModel match;

    public GameProof(Context context, GameModel game, MatchModel match) {
        super(context, game, match);

        this.game = game;
        this.match = match;

        build(context);
    }

    private void build(Context context) { buildContents(context); }

    private void buildContents(Context context) {

        GameProofHeader header = new GameProofHeader(context, this.match);
        GameProofScore scoreLine = new GameProofScore(context, this.match);

        ImageListAppendable imageList = new ImageListAppendable(context, this.images);

        this.addView(header);
        this.addView(scoreLine);
        this.addView(imageList);
    }
}
