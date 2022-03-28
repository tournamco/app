package co.tournam.members;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class MembersListItem extends LinearLayout {
    private UserModel user;

    private ImageView logo;
    private TextView playerName;

    public MembersListItem(Context context, UserModel user) {
        super(context);

        this.user = user;

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
        inflater.inflate(R.layout.row_team, this, true);

        logo = (ImageView) findViewById(R.id.imaget);
        playerName = (TextView) findViewById(R.id.namet);

        setName(user.getGamerTag());
        //setIcon(user.getIcon());
    }

    private void setName(String name) { playerName.setText(name);}

    private void setIcon(int imageID) { logo.setImageResource(imageID);}
}