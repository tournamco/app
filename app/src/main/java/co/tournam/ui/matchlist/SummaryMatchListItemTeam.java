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

    /**
     * @param context the current context
     * @param team    the team being added to the list
     * @param score   the score being added to the list
     * @param winner  the winner being added to the summarymatchlist.
     */
    public SummaryMatchListItemTeam(Context context, TeamModel team, int score, boolean winner) {
        super(context);

        this.team = team;
        this.score = score;
        this.winner = winner;

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
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(context, 16), dpToPx(context, 2), dpToPx(context, 16), dpToPx(context, 2));

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
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
            if (team.getIcon() != null || team.getIcon().equals("null")) {
                new DownloadImageWorker(image -> iconImage.setImageBitmap(image)).execute(team.getIcon());
            }
            setName(team.getName());
        }

        setScore(score);
        setWinner(winner);
    }

    /**
     * A setter for the name.
     *
     * @param name the name to be set
     */
    private void setName(String name) {
        nameText.setText(name);
    }

    /**
     * A setter for the score.
     *
     * @param score the score to be set
     */
    private void setScore(int score) {
        scoreText.setText(Integer.toString(score));
    }

    /**
     * A setter for whether a layout item is a winner or not
     *
     * @param winner the winner to be set
     */
    private void setWinner(boolean winner) {
        if (winner) {
            scoreText.setTextColor(Color.BLACK);
        } else {
            scoreText.setTextColor(Color.GRAY);
        }
    }

    /**
     * @param context the current context
     * @param dp      the size in dp
     * @return the size in px.
     */
    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
