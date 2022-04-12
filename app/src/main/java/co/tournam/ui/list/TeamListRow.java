package co.tournam.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class TeamListRow extends LinearLayout {

    private TeamModel team;
    private String buttonText;
    private ImageView teamIcon;
    private TextView teamName;
    public Button actionButton;
    private TeamList.TeamListActionListener listener;

    public TeamListRow(Context context, TeamModel team, String buttonText, TeamList.TeamListActionListener listener) {
        super(context);

        this.team = team;
        this.buttonText = buttonText;
        this.listener = listener;

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    public void buildContents(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_team, this, true);

        teamIcon = (ImageView) findViewById(R.id.imaget);
        teamName = (TextView) findViewById(R.id.namet);
        actionButton = (Button) findViewById(R.id.button2);
        actionButton.setOnClickListener(view -> {
            listener.onTeamSelected(team);
        });
        setButtonText(buttonText);

        new DownloadImageWorker(icon -> teamIcon.setImageBitmap(icon)).execute(team.getIcon());
        teamName.setText(team.getName());

    }

    public void setButtonText(String text) {
        actionButton.setText(text);
    }
}
