package co.tournam.ui.teamscore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
//        Bitmap imageID = this.team.getImage();
//        backend.getImage(imageID);
//        teamIcon.setImageBitmap(new Bitmap);   TODO: ONCE WE HAVE BACKEND SET UP

        teamName = findViewById(R.id.teamscore_icon_name);
        teamName.setText(this.team.getName());




    }
}