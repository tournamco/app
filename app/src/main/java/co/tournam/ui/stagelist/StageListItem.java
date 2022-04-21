package co.tournam.ui.stagelist;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.models.StageModel;
import co.tournam.schedule.R;
import co.tournam.schedule.StageActivity;

public class StageListItem extends LinearLayout {

    private StageModel stage;
    private Button button;
    private TournamentModel tournament;
    private String tournamentID;
    private int tournamentIndex;

    public StageListItem(Context context, StageModel stage, TournamentModel tournamentMod) {
        super(context);

        this.stage = stage;
        this.tournament = tournamentMod;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stagelist_row_base, this, true);

        tournamentID = tournament.getId();
        List<StageModel> stages = tournament.getStages();

        tournamentIndex = stages.indexOf(stage);

        button = findViewById(R.id.stagelist_button);
        button.setText(stage.getName());
        button.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putString("tournamentID", tournamentID);
            bundle.putInt("stageIndex", tournamentIndex);
            Intent intent = new Intent(context, StageActivity.class).setFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            context.startActivity(intent);

        });
    }
}
