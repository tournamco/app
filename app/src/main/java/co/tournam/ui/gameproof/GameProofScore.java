package co.tournam.ui.gameproof;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import co.tournam.models.MatchModel;
import co.tournam.schedule.R;
import co.tournam.ui.table.Score;

public class GameProofScore extends LinearLayout {

    private TextView team1;
    private TextView team2;
    private MatchModel match;
    private View score;

    public GameProofScore(Context context, MatchModel match) {
        super(context);

        this.match = match;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_proof_scoreline, this, true);

        team1 = findViewById(R.id.game_proof_team1);
        team1.setText(match.getTeams().get(0).getName());

        score = findViewById(R.id.game_proof_score);
        TextView score1 = score.findViewById(R.id.score1);
        score1.setText(Integer.toString(match.getScore(match.getTeams().get(0))));

        TextView score2 = score.findViewById(R.id.score2);
        score2.setText(Integer.toString(match.getScore(match.getTeams().get(1))));

        team2 = findViewById(R.id.game_proof_team2);
        team2.setText(match.getTeams().get(1).getName());
    }
}