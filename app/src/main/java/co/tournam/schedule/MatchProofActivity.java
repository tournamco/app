package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import co.tournam.api.ApiErrors;
import co.tournam.api.ProofHandler;
import co.tournam.api.TeamHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.gameproof.GameProof;
import co.tournam.ui.header.SmallHeader;

public class MatchProofActivity extends AppCompatActivity {

    //Variable Declarations
    private Context context;
    private MatchModel match;
    private String teamKey;
    private int proofIndex;
    private LinearLayout proofContainer;
    private DefaultButton addButton;
    private int lastAddedIndex;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    //On create method of the My Profile Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String matchId;

        if (b != null) {
            matchId = b.getString("matchid");
            teamKey = b.getString("teamkey");
        } else {
            return;
        }

        //Open Gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Bitmap newImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                            new UploadImageWorker(imageId -> {
                                GameProof proof = (GameProof) proofContainer.getChildAt(lastAddedIndex);
                                if (proof != null) {
                                    proof.addImage(imageId, newImage);
                                }
                            }).execute(newImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        setContentView(R.layout.activity_match_proof);

        context = this.getApplicationContext();
        loadMatch(matchId);
    }

    //Gets the information about the selected match from the server and builds the activity
    private void loadMatch(String matchId) {
        TeamHandler.matchInfo(matchId, response -> {
            match = response;
            build();
        });
    }

    //Builds the activity screen
    private void build() {
        LinearLayout addButtonContainer = (LinearLayout) findViewById(R.id.add_button);
        addButton = new DefaultButton(context, "Add Game");
        addButton.button.setOnClickListener(v -> {
            createProof(proofIndex);
            proofIndex++;
            checkGameProofSize();
        });
        addButtonContainer.addView(addButton);

        DefaultButton finishButton = new DefaultButton(context, "Finish");
        finishButton.button.setOnClickListener(v -> {
            finishMatch();
        });

        LinearLayout headerContainer = (LinearLayout) findViewById(R.id.header);
        headerContainer.addView(new SmallHeader(context, "Match proof", () -> finish(), finishButton));
        proofContainer = (LinearLayout) findViewById(R.id.proofs);

        for (int i = 0; i < match.getGames().size(); i++) {
            GameModel game = match.getGames().get(i);
            if (game.getProofs().get(teamKey) == null) continue;

            proofContainer.addView(new GameProof(context, game, i, match, teamKey, index -> {
                lastAddedIndex = index;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);
            }));
            proofIndex++;

            checkGameProofSize();
        }
    }

    //Sends the information to the server that the selected match is finished
    private void finishMatch() {
        TeamHandler.finishMatch(match.getId(), () -> finish());
    }

    //Creates a new proof in the server
    private void createProof(int gameIndex) {
        ProofHandler.create(match.getId(), gameIndex, proofId ->
                proofContainer.addView(new GameProof(context, match.getGames().get(gameIndex),
                gameIndex, match, teamKey, proofId, index -> {
            lastAddedIndex = index;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            someActivityResultLauncher.launch(intent);
        })));
    }

    /*
     * Checks whether the amount of games that are given proof match the maximum amount of games in
     * the match
     */
    private void checkGameProofSize() {
        if (proofIndex < match.getGames().size()) {
            return;
        }

        addButton.setVisibility(View.GONE);
    }
}
