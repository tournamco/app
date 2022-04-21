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

    /**
     * Constructor for GameProofHeader
     *
     * @param context   the current context
     * @param gameIndex the index of the game of which proof is needed
     */
    public GameProofHeader(Context context, int gameIndex) {
        super(context);

        this.gameIndex = gameIndex;

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
     * BuildContents class takes the context and uses it to inflate the layout create the header.
     * and set the text for the header
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_proof_header, this, true);

        textView = findViewById(R.id.game_proof_header);
        textView.setText("Game " + (gameIndex + 1));
    }
}
