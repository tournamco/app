package co.tournam.ui.dispute;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ProofHandler;
import co.tournam.api.TeamHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;
import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class Dispute extends LinearLayout {

    private DisputeModel dispute;
    private LinearLayout disputeColumns;
    private MatchModel match;
    private DisputeResolvedListener listener;

    private Context context;

    /**
     * The constructor for the Dispute class
     *
     * @param context  the current context
     * @param dispute  the model of the dispute being visualized
     * @param listener the listener used for the dispute
     */
    public Dispute(Context context, DisputeModel dispute, DisputeResolvedListener listener) {
        super(context);

        this.dispute = dispute;
        this.context = context;
        this.listener = listener;

        loadMatch();
    }

    /**
     * loadMatch attempts to create a new match, success it will build the UI
     * on failure it will print an error message.
     */
    private void loadMatch() {
        TeamHandler.matchInfo(dispute.getMatchId(), response -> {
            match = response;
            build();
        });
    }

    /**
     * Build method provides the option of modifying the layout before building
     */
    private void build() {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        buildContents();
    }

    /**
     * buildContents method inflates the layout and visualizes the dispute, it does this by
     * setting the name of each team, the name of the game and match along with each team's icon.
     * Afterwards it finds the dispute in the game model and passes the data on to a further
     * function.
     */
    private void buildContents() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dispute, this, true);

        TeamModel teamA = match.getTeams().get(match.getKeys().get(0));
        TeamModel teamB = match.getTeams().get(match.getKeys().get(1));

        TextView teamNameA = (TextView) findViewById(R.id.teamNameA);
        teamNameA.setText(teamA.getName());
        TextView teamNameB = (TextView) findViewById(R.id.teamNameB);
        teamNameB.setText(teamB.getName());
        TextView gameName = (TextView) findViewById(R.id.gameName);
        gameName.setText("Game " + (dispute.getGameIndex() + 1));
        TextView matchName = (TextView) findViewById(R.id.matchName);
        matchName.setText(match.getName());
        ImageView teamIconA = (ImageView) findViewById(R.id.teamIconA);
        ImageView teamIconB = (ImageView) findViewById(R.id.teamIconB);
        new DownloadImageWorker(image -> teamIconA.setImageBitmap(image)).execute(teamA.getIcon());
        new DownloadImageWorker(image -> teamIconB.setImageBitmap(image)).execute(teamB.getIcon());
        disputeColumns = (LinearLayout) findViewById(R.id.disputeColumns);
        GameModel game = match.getGames().get(dispute.getGameIndex());
        Map<String, String> proofs = game.getProofs();

        loadProofColumn(proofs.get(match.getKeys().get(0)), match.getKeys().get(0));
        loadProofColumn(proofs.get(match.getKeys().get(1)), match.getKeys().get(1));
    }

    /**
     * loadProofColumn connects to the backend and attempts to obtain the proof columns
     * on success it adds them to the UI, on failure it prints an error message.
     *
     * @param proofId id of the proof
     * @param key     key of the proof
     */
    private void loadProofColumn(String proofId, String key) {
        ProofHandler.info(proofId, proof ->
                disputeColumns.addView(new DisputeColumn(context, proof, dispute.getId(),
                key, Dispute.this, listener)));
    }

    /**
     * Necessary interface for the DisputeResolvedListener
     */
    public interface DisputeResolvedListener {
        void disputeResolved(Dispute dispute);
    }
}
