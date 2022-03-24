package co.tournam.ui.header;

import android.content.Context;
import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class header extends LinearLayout{
    private TeamModel team;
    private headerTitle teamsOrMembers;
    private TextView title;
    private TextView memberAmountText;

    public header(Context context, TeamModel team, headerTitle teamsOrMembers) {
        super(context);
        this.team = team;
        this.teamsOrMembers = teamsOrMembers;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header, this, true);

        title = (TextView) findViewById(R.id.teamsOrMembers);
        memberAmountText = (TextView) findViewById(R.id.memberNumber);

        if( teamsOrMembers == headerTitle.TEAMS) {
            setTitle("Teams");
            setMemberAmount(null);
        }
        else if( teamsOrMembers == headerTitle.MEMBERS) {
            setTitle("Members");
            setMemberAmount(String.valueOf(team.getCurrentMemberAmount()) + " / " + String.valueOf(team.getCurrentMemberAmount()));
        }
    }

    private void setTitle(String name) { title.setText(name);}

    private void setMemberAmount (String memberRatio) { memberAmountText.setText(memberRatio);}

}
