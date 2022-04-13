package co.tournam.ui.teamscore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.models.TeamModel;
import co.tournam.schedule.OurTeamActivity;
import co.tournam.schedule.R;

public class TeamScoreIconItem extends LinearLayout {

    private TeamModel team;
    private ImageView teamIcon;
    private TextView teamName;

    public TeamScoreIconItem(Context context, TeamModel team) {
        super(context);

        this.team = team;
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.team_score_icon, this, true);

        setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("teamid", team.getID());
            Intent intent = new Intent(getContext(), OurTeamActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

        teamIcon = findViewById(R.id.teamscore_icon_image);
        teamName = findViewById(R.id.teamscore_icon_name);

        if(team != null) {
            new DownloadImageWorker(image -> teamIcon.setImageBitmap(image)).execute(team.getIcon());
            teamName.setText(this.team.getName());
        }
        else {
            teamName.setText("Awaiting opponent");
        }
    }
}