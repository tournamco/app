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
    private StageOptionTime dateSpan;
    private StageOptionTime timeSpan;


    public StageOption(Context context){
        super(context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

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

        dateSpan = new StageOptionTime(context, "Date");
        this.addView(dateSpan);

        timeSpan = new StageOptionTime(context, "Time");
        this.addView(timeSpan);

    }

    public TournamentModel.CreateStageModel createStageFromEntry() {

        TournamentModel.StageTypes type;
        String selected = spinner.getSelectedItem().toString();
        if (selected.equals("Pools")) {
            type = TournamentModel.StageTypes.POOLS;
        } else if (selected.equals("Single Elim")) {
            type = TournamentModel.StageTypes.SINGLE_ELIMINATION;
        } else if (selected.equals("Double Elim")) {
            type = TournamentModel.StageTypes.DOUBLE_ELIMINATION;
        } else {
            type = TournamentModel.StageTypes.SWISS_ELIMINATION;
        }

        Map<String, Integer> options = new HashMap<>();
        TournamentModel.CreateStageModel sample = new TournamentModel.CreateStageModel(
                type,
                this.name.getEntry(),
                Integer.parseInt(this.noOfParticipants.getEntry()),
                this.dateSpan.getFirstEntry(),
                this.dateSpan.getSecondEntry(),
                this.timeSpan.getFirstEntry(),
                this.timeSpan.getSecondEntry(),
                options);

        return sample;



    }
}
