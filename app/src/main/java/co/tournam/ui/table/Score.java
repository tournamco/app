package co.tournam.ui.table;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.schedule.R;

public class Score extends LinearLayout {

    private List<Integer> scores;

    public Score(Context context, List<Integer> scores) {
        super(context);

        this.scores = scores;


        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(context, 16), dpToPx(context, 2), dpToPx(context, 16), dpToPx(context, 2));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.score_cells, this, true);

        TextView score1Text = findViewById(R.id.score1);
        System.out.println("score1Text " + scores.get(0));
        System.out.println("score2Text " + scores.get(1));
        score1Text.setText(Integer.toString(scores.get(0)));

        TextView score2Text = findViewById(R.id.score2);
        score2Text.setText(Integer.toString(scores.get(1)));
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }

}
