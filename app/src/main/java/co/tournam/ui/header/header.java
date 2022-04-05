package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.TeamModel;
import co.tournam.schedule.R;


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

        switch (teamsOrMembers) {
            case TEAMS:
                setTitle("Teams");
                setMemberAmount(null);
                break;
            case MEMBERS:
                setTitle("Members");
                setMemberAmount(String.valueOf(team.getCurrentMemberAmount()) + " / " + String.valueOf(team.getCurrentMemberAmount()));
                break;
            case INVITE:
                setTitle("Invite");
                setMemberAmount(null);
                break;
            case MATCHES:
                setTitle("Matches");
                setMemberAmount(null);
                break;
            case STAGES:
                setTitle("Stages");
                setMemberAmount(null);
                break;
            case INFORMATION:
                setTitle("Information");
                setMemberAmount(null);
                break;
            case PROOF_OF_SCORE:
                setTitle("Proof of Score");
                setMemberAmount(null);
                break;
            case CHANGE_PASSWORD:
                setTitle("Change Password");
                setMemberAmount(null);
                break;
            case STATISTICS:
                setTitle("Statistics");
                setMemberAmount(null);


        }
    }

    private void setTitle(String name) { title.setText(name);}

    private void setMemberAmount (String memberRatio) { memberAmountText.setText(memberRatio);}

}
