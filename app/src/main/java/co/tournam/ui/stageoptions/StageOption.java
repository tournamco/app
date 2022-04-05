package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import co.tournam.models.TournamentModel;
import co.tournam.schedule.R;
public class StageOption extends LinearLayout {

    private Spinner spinner;
    private StageOptionBody name;
    private StageOptionBody noOfParticipants;
//    private StageOptionBody noOfWinners;
    private StageOptionTime dateDuration;
    private StageOptionTime timeDuration;


    public StageOption(Context context){
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_stage_options, this, true);

        spinner = findViewById(R.id.stageoption_spinner);
//        spinner.setAdapter(new ArrayAdapter<TournamentModel.StageTypes>(context, R.layout.sample_stage_options));

        name = new StageOptionBody(context, "Name of Stage");
        this.addView(name);

        noOfParticipants = new StageOptionBody(context, "Number of Participants");
        this.addView(noOfParticipants);

//        noOfWinners = new StageOptionBody(context, "Number of Winners");
//        this.addView(noOfWinners);

        dateDuration = new StageOptionTime(context, "Date");
        this.addView(dateDuration);

        timeDuration = new StageOptionTime(context, "Time");
        this.addView(timeDuration);

    }

    public TournamentModel.CreateStageModel createStageFromEntry() {

        Map<String, Integer> options = new HashMap<>();
        TournamentModel.CreateStageModel sample = new TournamentModel.CreateStageModel(
                (TournamentModel.StageTypes) spinner.getSelectedItem(),
                this.name.getEntry(),
                Integer.parseInt(this.noOfParticipants.getEntry()),
                this.dateDuration.getFirstEntry(),
                this.dateDuration.getSecondEntry(),
                this.timeDuration.getFirstEntry(),
                this.timeDuration.getSecondEntry(),
                options);

        return sample;



    }
}
