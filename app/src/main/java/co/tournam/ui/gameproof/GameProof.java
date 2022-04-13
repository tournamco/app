package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.activity.ComponentActivity;
import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
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
    private String proofId;
    private String key;
    private int gameIndex;
    private ProofModel proofModel;
    private List<String> proofImages;
    private ImageListAppendable imageList;
    private Context context;
    private ImageListAppendable.ImageAppendListener listener;

    public GameProof(Context context, GameModel game, int gameIndex,
                     MatchModel match, String key, ImageListAppendable.ImageAppendListener listener) {
        super(context, game, match);

        this.game = game;
        this.match = match;
        this.key = key;
        this.gameIndex = gameIndex;
        this.context = context;
        this.proofId = game.getProofs().get(key);
        this.listener = listener;

        build(context);
    }

    public GameProof(Context context, GameModel game, int gameIndex,
                     MatchModel match, String key, String proofId, ImageListAppendable.ImageAppendListener listener) {
        super(context, game, match);

        this.game = game;
        this.match = match;
        this.key = key;
        this.gameIndex = gameIndex;
        this.context = context;
        this.proofId = proofId;
        this.listener = listener;

        build(context);
    }

    private void build(Context context) {
        buildContents(context);

        setPadding(16, 0, 16, 32);
    }

    private void buildContents(Context context) {
        ProofHandler.info(proofId, new ProofHandler.InfoComplete() {
            @Override
            public void success(ProofModel proof) {
                proofModel = proof;
                proofImages = proofModel.getImages();
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void addImage(String imageId, Bitmap image) {
        ProofHandler.addImage(match.getId(), proofId, imageId, new ProofHandler.AddImageComplete() {
            @Override
            public void success() {
                imageList.addImage(image);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        GameProofHeader header = new GameProofHeader(context, gameIndex);
        GameProofScore scoreLine = new GameProofScore(context, this.match, this.key);
        imageList = new ImageListAppendable(context, this.gameIndex, new ArrayList<>(), listener);

        for (String proofImage : proofImages) {
            new DownloadImageWorker(image -> imageList.addImage(image)).execute(proofImage);
        }

        this.addView(header);
        this.addView(scoreLine);
        this.addView(imageList);
    }
}
