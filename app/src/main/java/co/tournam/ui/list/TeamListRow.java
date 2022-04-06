package co.tournam.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class TeamListRow extends LinearLayout {

    private String teamID;
    private String buttonText;
    private ImageView teamIcon;
    private TextView teamName;
    public Button actionButton;

    public TeamListRow(Context context, String teamID, String buttonText) {
        super(context);

        this.teamID = teamID;
        this.buttonText = buttonText;

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
        setButtonText(buttonText);

        TeamHandler.info(teamID, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                teamIcon.setImageBitmap(ImageLoader.loadImage(team.getIcon(), context));
                teamName.setText(team.getName());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });

    }

    public void setButtonText(String text) {
        actionButton.setText(text);
    }

    public void changeTeam(String teamID) {
        this.teamID = teamID;
    }

    public String getTeam() {
        return this.teamID;
    }
}
