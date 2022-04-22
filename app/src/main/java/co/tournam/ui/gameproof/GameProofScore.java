package co.tournam.ui.gameproof;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.ProofHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;
import co.tournam.schedule.R;
import co.tournam.ui.table.Score;

public class GameProofScore extends LinearLayout {

    private TextView team1;
    private TextView team2;
    private MatchModel match;
    private String key;
    private ProofModel proof;
    private Context context;

    /**
     * The constructor for GameProofScore
     *
     * @param context the current context
     * @param match   the matchmodel used
     * @param key     the key for the match
     * @param proof   the proofmodel used to visualize the proof.
     */
    public GameProofScore(Context context, MatchModel match, String key, ProofModel proof) {
        super(context);

        this.match = match;
        this.key = key;
        this.proof = proof;
        this.context = context;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    /**
     * The buildContents method inflates the gameproofscore layout and adds the
     * teams and their respective scores.
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_proof_scoreline, this, true);

        team1 = findViewById(R.id.game_proof_team1);
        team1.setText(match.getKeys().get(0).equals(key) ? "them" : "us");

        EditText score1 = findViewById(R.id.score1);
        score1.setText(Integer.toString(proof.getScores().get(match.getKeys().get(0))));
        score1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = score1.getText().toString();

                if (value.equals("")) return;

                updateScores(match.getKeys().get(0), Integer.parseInt(value));
            }
        });

        EditText score2 = findViewById(R.id.score2);
        score2.setText(Integer.toString(proof.getScores().get(match.getKeys().get(1))));
        score2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = score2.getText().toString();

                if (value.equals("")) return;

                updateScores(match.getKeys().get(1), Integer.parseInt(value));
            }
        });

        team2 = findViewById(R.id.game_proof_team2);
        team2.setText(match.getKeys().get(1).equals(key) ? "them" : "us");
    }

    /**
     * Updates a specific score based on the key and value given.
     *
     * @param key   the key string is used to obtain the score.
     * @param value the value int is used to obtain the score.
     */
    private void updateScores(String key, int value) {
        Map<String, Integer> scores = proof.getScores();
        scores.put(key, value);
        proof.setScores(scores);

        ProofHandler.setScores(match.getId(), proof.getId(), scores, () ->
                Toast.makeText(context, "Scores Updated", Toast.LENGTH_LONG).show());
    }
}