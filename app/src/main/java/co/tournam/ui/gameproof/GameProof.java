package co.tournam.ui.gameproof;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;

import co.tournam.api.ProofHandler;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;

import co.tournam.ui.imagelist.ImageListAppendable;

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

    /**
     * The constructor for the game proof, obtaining the proof id by using the game model
     *
     * @param context   the current context
     * @param game      the gameModel being used
     * @param gameIndex the index of the game that the proof belongs to
     * @param match     the current match
     * @param key       the key for the match
     * @param listener  the listener being used
     */
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

    /**
     * The constructor for the game proof, obtaining the proof id by using the parameter
     *
     * @param context   the current context
     * @param game      the gameModel being used
     * @param gameIndex the index of the game that the proof belongs to
     * @param match     the current match
     * @param key       the key for the match
     * @param listener  the listener being used
     * @param proofId   the id of the proof
     */
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

    /**
     * Build method provides the option of modifying the layout before building
     */
    private void build(Context context) {
        buildContents(context);

        setPadding(16, 0, 16, 32);
    }

    /**
     * Buildcontents builds the total visualization of the model
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        ProofHandler.info(proofId, proof -> {
            proofModel = proof;
            proofImages = proofModel.getImages();
            build();
        });
    }

    /**
     * addImage adds an image to the UI
     *
     * @param imageId the id of the image
     * @param image   the bitmap of the image
     */
    public void addImage(String imageId, Bitmap image) {
        ProofHandler.addImage(match.getId(), proofId, imageId, () -> imageList.addImage(image));
    }

    /**
     * alternative build method
     */
    private void build() {
        GameProofHeader header = new GameProofHeader(context, gameIndex);
        GameProofScore scoreLine = new GameProofScore(context, this.match, this.key, proofModel);
        imageList = new ImageListAppendable(context, this.gameIndex, new ArrayList<>(), listener);

        for (String proofImage : proofImages) {
            new DownloadImageWorker(image -> imageList.addImage(image)).execute(proofImage);
        }

        this.addView(header);
        this.addView(scoreLine);
        this.addView(imageList);
    }
}
