package co.tournam.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.models.TeamModel;
import co.tournam.schedule.OurTeamActivity;
import co.tournam.schedule.R;

public class TeamListRow extends LinearLayout {

    private TeamModel team;
    private String buttonText;
    private ImageView teamIcon;
    private TextView teamName;
    public Button actionButton;
    private TeamList.TeamListActionListener listener;

    /**
     * The constructor for the TeamListRow
     *
     * @param context    the current context
     * @param team       the selected TeamModel.
     * @param buttonText the text for the button
     * @param listener   the listener for the class.
     */
    public TeamListRow(Context context, TeamModel team, String buttonText, TeamList.TeamListActionListener listener) {
        super(context);

        this.team = team;
        this.buttonText = buttonText;
        this.listener = listener;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_team, this, true);

        teamIcon = (ImageView) findViewById(R.id.imaget);
        teamIcon.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("teamid", team.getID());
            Intent intent = new Intent(context, OurTeamActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        teamName = (TextView) findViewById(R.id.namet);
        actionButton = (Button) findViewById(R.id.button2);
        actionButton.setOnClickListener(view -> {
            listener.onTeamSelected(team);
        });

        if (buttonText == null) {
            actionButton.setVisibility(View.GONE);
        } else {
            actionButton.setText(buttonText);
        }

        new DownloadImageWorker(icon -> teamIcon.setImageBitmap(icon)).execute(team.getIcon());
        teamName.setText(team.getName());

    }
}
