package co.tournam.ui.dispute;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.widget.LinearLayoutCompat;
import co.tournam.models.ProofModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.imagelist.ImageListVertical;
import co.tournam.ui.table.Score;

public class DisputeColumn extends LinearLayout {
    private ProofModel proof;

    public DisputeColumn(Context context, ProofModel proof) {
        super(context);

        this.proof = proof;

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
        this.addView(new DefaultButton(context, "Agree"));
    }
}
