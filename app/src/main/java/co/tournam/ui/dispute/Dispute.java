package co.tournam.ui.dispute;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.DisputeModel;
import co.tournam.schedule.R;

public class Dispute extends LinearLayout {

    private DisputeModel dispute;
    private TextView teamNameA;
    private TextView teamNameB;
    private TextView gameName;
    private TextView matchName;
    private ImageView teamIconA;
    private ImageView teamIconB;
    private LinearLayout disputeColumns;

    public Dispute(Context context, DisputeModel dispute) {
        super(context);

        this.dispute = dispute;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dispute, this, true);

        DisputeModel.DisputeTeamModel teamA = this.dispute.getTeams().get(this.dispute.getKeys().get(0));
        DisputeModel.DisputeTeamModel teamB = this.dispute.getTeams().get(this.dispute.getKeys().get(1));

        teamNameA = (TextView)findViewById(R.id.teamNameA);
        teamNameA.setText(teamA.getName());
        teamNameB = (TextView)findViewById(R.id.teamNameB);
        teamNameB.setText(teamB.getName());
        gameName = (TextView)findViewById(R.id.gameName);
        gameName.setText("Game " + (this.dispute.getGameIndex() + 1));
        matchName = (TextView)findViewById(R.id.matchName);
        matchName.setText(this.dispute.getMatchName());
        teamIconA = (ImageView)findViewById(R.id.teamIconA);
        teamIconB = (ImageView)findViewById(R.id.teamIconB);
        disputeColumns = (LinearLayout)findViewById(R.id.disputeColumns);

        for(String teamKey : this.dispute.getKeys()) {
            this.addView(new DisputeColumn(context, this.dispute.getProofs().get(teamKey)));
        }
    }
}
