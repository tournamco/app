package co.tournam.ui.dispute;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.widget.LinearLayoutCompat;

import androidx.constraintlayout.widget.ConstraintLayout;

import co.tournam.api.ApiErrors;
import co.tournam.api.DisputeHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.ProofModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.imagelist.ImageListVertical;
import co.tournam.ui.table.Score;

public class DisputeColumn extends LinearLayout {
    private ProofModel proof;
    private String disputeId;
    private String key;

    private Dispute.DisputeResolvedListener listener;
    private Dispute dispute;

    /**
     * The Constructor of the DisputeColumn class.
     *
     * @param context   the current context
     * @param proof     the proof model
     * @param disputeId the id of the dispute
     * @param key       the key of the dispute
     * @param dispute   the model of the dispute
     * @param listener  the disputeresolved listener.
     */
    public DisputeColumn(Context context, ProofModel proof, String disputeId, String key, Dispute dispute, Dispute.DisputeResolvedListener listener) {
        super(context);

        this.proof = proof;
        this.disputeId = disputeId;
        this.key = key;
        this.listener = listener;
        this.dispute = dispute;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     */
    private void build(Context context) {
        setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1
        ));
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        buildContents(context);
    }

    /**
     * buildContents method adds the score and imageLists, uses disputehandler.
     *
     * @param context current context
     */
    private void buildContents(Context context) {
        this.addView(new Score(context, new ArrayList<>(this.proof.getScores().values())));
        this.addView(new ImageListVertical(context, new ArrayList<>()));
        DefaultButton button = new DefaultButton(context, "Agree");
        button.setPadding(dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10));
        button.setOnClickListener(view -> DisputeHandler.resolve(disputeId, key, () ->
                listener.disputeResolved(dispute)));
        this.addView(button);
    }

    /**
     * Converts a dp value to px.
     *
     * @param context the current context
     * @param dp      the dp that needs to be converted
     * @return the dp value in px
     */
    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
