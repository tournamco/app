package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.ProofHandler;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;
import co.tournam.ui.imagelist.AbstractImageList;
import co.tournam.ui.imagelist.ImageListAddItem;
import co.tournam.ui.imagelist.ImageListAppendable;
import co.tournam.ui.imagelist.ImageListItem;

public class GameProof extends AbstractGameProof {

    private GameModel game;
    private MatchModel match;
    private String id;
    private ProofModel proofModel;
    private List<String> proofImages;

    public GameProof(Context context, GameModel game, MatchModel match, String id) {
        super(context, game, match);

        this.game = game;
        this.match = match;
        this.id = id;

        build(context);
    }

    private void build(Context context) { buildContents(context); }

    private void buildContents(Context context) {

        GameProofHeader header = new GameProofHeader(context, this.match);
        GameProofScore scoreLine = new GameProofScore(context, this.match);
        proofModel = null;
        proofImages = new ArrayList<>();
        Map<String, String> proofs = game.getProofs();
        String proofID = proofs.get(id);
        ProofHandler.info(proofID, new ProofHandler.InfoComplete() {
            @Override
            public void success(ProofModel proof) {
                proofModel = proof;
                proofImages = proofModel.getImages();
            }

            @Override
            public void failure(ApiErrors error, String message) {

            }
        });
        List<Bitmap> proofMap = new ArrayList<>();
        for (String proofImage : proofImages) {
             proofMap.add(ImageLoader.loadImage(proofImage, context));
        }
        ImageListAppendable imageList = new ImageListAppendable(context, proofMap);

        this.addView(header);
        this.addView(scoreLine);
        this.addView(imageList);
    }
}
