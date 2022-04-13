package co.tournam.ui.gameproof;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.MatchModel;
import co.tournam.schedule.R;

public class GameProofHeader extends LinearLayout {

    private TextView textView;
    private int gameIndex;

    public GameProofHeader(Context context, int gameIndex) {
        super(context);

        this.gameIndex = gameIndex;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_proof_header, this, true);

        textView = findViewById(R.id.game_proof_header);
        textView.setText("Game " + (gameIndex + 1));
    }
}
