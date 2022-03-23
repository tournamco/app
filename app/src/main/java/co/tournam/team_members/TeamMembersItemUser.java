package co.tournam.team_members;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class TeamMembersItemUser extends LinearLayout {
    private UserModel user;
    private boolean leftTeam;

    private TextView memberName;
    private ImageView memberIcon;

    public TeamMembersItemUser(Context context, UserModel user
        , boolean leftTeam) {
        super(context);

        this.user = user;
        this.leftTeam = leftTeam;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(leftTeam == true) {
            inflater.inflate(R.layout.team_member, this, true);
        } else {
            inflater.inflate(R.layout.team_member_right, this, true);
        }

        memberName = (TextView) findViewById(R.id.Name);
        memberIcon = (ImageView) findViewById(R.id.Icon);

        setName(user.getGamerTag());
        setIcon(user.getIcon());
    }

    private void setName(String name) { memberName.setText(name);}

    private void setIcon(int imageID) { memberIcon.setImageResource(imageID);}
}
