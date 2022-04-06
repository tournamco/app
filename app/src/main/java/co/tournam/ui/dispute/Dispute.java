package co.tournam.ui.dispute;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.ProofHandler;
import co.tournam.api.TeamHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;
import co.tournam.schedule.MatchProofActivity;
import co.tournam.schedule.R;

public class Dispute extends LinearLayout {

    private List<DisputeModel> disputes;
    private TextView teamNameA;
    private TextView teamNameB;
    private TextView gameName;
    private TextView matchName;
    private ImageView teamIconA;
    private ImageView teamIconB;
    private LinearLayout disputeColumns;
    private MatchModel match;
    private ProofModel proofModelA;
    private ProofModel proofModelB;


    public Dispute(Context context, List<DisputeModel> disputes) {
        super(context);

        this.disputes = disputes;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        buildContents(context);
    }

    private void buildContents(Context context) {
       LayoutInflater inflater = (LayoutInflater) context
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       inflater.inflate(R.layout.dispute, this, true);

       for (DisputeModel dispute : disputes) {
           String matchIdentifier = dispute.getMatchId();
           TeamHandler.matchInfo(matchIdentifier, new TeamHandler.MatchInfoComplete() {
               @Override
               public void success(MatchModel response) {
                   match = response;
               }

               @Override
               public void failure(ApiErrors error, String message) {

               }
           });

           teamNameA = (TextView) findViewById(R.id.teamNameA);
           teamNameA.setText(match.getTeams().get(0).getName());
           teamNameB = (TextView) findViewById(R.id.teamNameB);
           teamNameB.setText(match.getTeams().get(1).getName());
           gameName = (TextView) findViewById(R.id.gameName);
           gameName.setText("Game " + (dispute.getGameIndex() + 1));
           matchName = (TextView) findViewById(R.id.matchName);
           matchName.setText(match.getName());
           teamIconA = (ImageView) findViewById(R.id.teamIconA);
           teamIconB = (ImageView) findViewById(R.id.teamIconB);
           disputeColumns = (LinearLayout) findViewById(R.id.disputeColumns);
           teamIconA.setImageBitmap(ImageLoader.loadImage(match.getTeams().get(0).getIcon(), context));
           teamIconA.setImageBitmap(ImageLoader.loadImage(match.getTeams().get(1).getIcon(), context));
           GameModel game = match.getGames().get(dispute.getGameIndex());
           Map<String, String> proofs = game.getProofs();
           String proofIDA = proofs.get(0);
           ProofHandler.info(proofIDA, new ProofHandler.InfoComplete() {
               @Override
               public void success(ProofModel proof) {
                   proofModelA = proof;
               }

               @Override
               public void failure(ApiErrors error, String message) {

               }
           });
           String proofIDB = proofs.get(1);
           ProofHandler.info(proofIDB, new ProofHandler.InfoComplete() {
               @Override
               public void success(ProofModel proof) {
                   proofModelB = proof;
               }

               @Override
               public void failure(ApiErrors error, String message) {

               }
           });

           this.addView(new DisputeColumn(context, proofModelA, dispute.getId()));
           this.addView(new DisputeColumn(context, proofModelB, dispute.getId()));
       }

    }
}
