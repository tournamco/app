package co.tournam.table;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.MatchModel;
import co.tournam.schedule.R;

public class Score extends LinearLayout{

    private MatchModel matchModel;




    public Score(Context context, MatchModel matchModel) {
        super(context);

        this.matchModel = matchModel;


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
        score1Text.setText(matchModel.getScore(matchModel.getTeams().get(0)));

        TextView score2Text= findViewById(R.id.score2);
        score2Text.setText(matchModel.getScore(matchModel.getTeams().get(1)));




    }



    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }

}
