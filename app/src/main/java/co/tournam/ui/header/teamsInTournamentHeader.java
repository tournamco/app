package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.TournamentModel;
import co.tournam.schedule.R;

public class teamsInTournamentHeader extends LinearLayout {

    TournamentModel tournament;
    private TextView title;
    private TextView memberAmountText;

    public teamsInTournamentHeader(Context context, TournamentModel tournament) {
        super(context);
        this.tournament = tournament;
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

        setTitle("Teams");
        setMemberAmount(String.valueOf(tournament.getCurrentAmountOfTeams()) + " / " + String.valueOf(tournament.getStages().get(0).getNumberOfParticipants()));
    }

    private void setTitle(String name) { title.setText(name);}

    private void setMemberAmount (String memberRatio) { memberAmountText.setText(memberRatio);}
}
