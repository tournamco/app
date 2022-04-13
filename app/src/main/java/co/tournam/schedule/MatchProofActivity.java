package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.ProofHandler;
import co.tournam.api.TeamHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.gameproof.GameProof;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListAppendable;

public class MatchProofActivity extends AppCompatActivity {

    private Context context;
    private MatchModel match;
    private String teamKey;
    private int proofIndex;
    private LinearLayout proofContainer;
    private DefaultButton addButton;
    private int lastAddedIndex;

    ActivityResultLauncher<Intent> someActivityResultLauncher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String matchId;

        if(b != null) {
            matchId = b.getString("matchid");
            teamKey = b.getString("teamkey");
        }
        else {
            return;
        }

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Bitmap newImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                            new UploadImageWorker(imageId -> {
                                GameProof proof = (GameProof)proofContainer.getChildAt(lastAddedIndex);
                                if(proof != null) {
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

    private void loadMatch(String matchId) {
        TeamHandler.matchInfo(matchId, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel response) {
                match = response;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        LinearLayout addButtonContainer = (LinearLayout)findViewById(R.id.add_button);
        addButton = new DefaultButton(context, "Add Game");
        addButton.button.setOnClickListener(v -> {
            createProof(proofIndex);
            proofIndex++;
            checkGameProofSize();
        });
        addButtonContainer.addView(addButton);

        DefaultButton finishButton = new DefaultButton(context, "Finish");
        finishButton.button.setOnClickListener(v -> {
            // TODO: send finish match call
            finish();
        });

        LinearLayout headerContainer = (LinearLayout)findViewById(R.id.header);
        headerContainer.addView(new SmallHeader(context, "Match proof", () -> finish(), finishButton));
        proofContainer = (LinearLayout)findViewById(R.id.proofs);

        for(int i = 0; i < match.getGames().size(); i++) {
            GameModel game = match.getGames().get(i);
            if(game.getProofs().get(teamKey) == null) continue;

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

    private void createProof(int gameIndex) {
        ProofHandler.create(match.getId(), gameIndex, new ProofHandler.CreateComplete() {
            @Override
            public void success(String proofId) {
                proofContainer.addView(new GameProof(context, match.getGames().get(gameIndex),
                    gameIndex, match, teamKey, proofId, index -> {
                        lastAddedIndex = index;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        someActivityResultLauncher.launch(intent);
                    }));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void checkGameProofSize() {
        if(proofIndex < match.getGames().size()) {
            return;
        }

        addButton.setVisibility(View.GONE);
    }
}
