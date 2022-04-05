package co.tournam.ui.teamscore;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.ImageLoader;
import co.tournam.models.TeamModel;
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

        teamIcon = findViewById(R.id.teamscore_icon_image);

        Bitmap bm = ImageLoader.loadImage(team.getIcon(), context);
        teamIcon.setImageBitmap(bm);

        teamName = findViewById(R.id.teamscore_icon_name);
        teamName.setText(this.team.getName());




    }
}