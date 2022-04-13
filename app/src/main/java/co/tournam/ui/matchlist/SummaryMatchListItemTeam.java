package co.tournam.ui.matchlist;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.DownloadImageWorker;
import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class SummaryMatchListItemTeam extends LinearLayout {
    private TeamModel team;
    private int score;
    private boolean winner;

    private TextView nameText;
    private TextView scoreText;
    private ImageView iconImage;

    public SummaryMatchListItemTeam(Context context, TeamModel team, int score, boolean winner) {
        super(context);

        this.team = team;
        this.score = score;
        this.winner = winner;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(context, 16), dpToPx(context, 2), dpToPx(context, 16), dpToPx(context, 2));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.match_list_item_team, this, true);

        nameText = (TextView) findViewById(R.id.name);
        scoreText = (TextView) findViewById(R.id.score);
        iconImage = (ImageView) findViewById(R.id.logo);

        if (team == null) {
            setName("Awaiting opponent");
        } else {
            new DownloadImageWorker(image -> iconImage.setImageBitmap(image)).execute(team.getIcon());
            setName(team.getName());
        }

        setScore(score);
        setWinner(winner);
    }

    private void setName(String name) {
        nameText.setText(name);
    }

    private void setScore(int score) {
        scoreText.setText(Integer.toString(score));
    }

    private void setWinner(boolean winner) {
        if(winner) {
            scoreText.setTextColor(Color.BLACK);
        }
        else {
            scoreText.setTextColor(Color.GRAY);
        }
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
