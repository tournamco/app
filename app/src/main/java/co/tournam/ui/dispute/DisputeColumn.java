package co.tournam.ui.dispute;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.widget.LinearLayoutCompat;

import co.tournam.api.ApiErrors;
import co.tournam.api.DisputeHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.ProofModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.imagelist.ImageListVertical;
import co.tournam.ui.table.Score;

public class DisputeColumn extends LinearLayout {
    private ProofModel proof;
    private String dispute;

    public DisputeColumn(Context context, ProofModel proof, String disputeID) {
        super(context);

        this.proof = proof;
        this.dispute = disputeID;

        build(context);
    }

    private void build(Context context) {
        setLayoutParams(new LinearLayoutCompat.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1
        ));
        setOrientation(LinearLayout.VERTICAL);
        buildContents(context);
    }

    private void buildContents(Context context) {
        this.addView(new Score(context, new ArrayList<>(this.proof.getScores().values())));
        this.addView(new ImageListVertical(context, new ArrayList<>()));
        DefaultButton button = new DefaultButton(context, "Agree");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisputeHandler.resolve(dispute, proof.getTeamId(), new DisputeHandler.ResolveComplete() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void failure(ApiErrors error, String message) {

                    }
                });
            }
        });
        this.addView(button);
    }
}
