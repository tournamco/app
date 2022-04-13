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

    public Spinner spinner;
    private StageOptionBody name;
    private StageOptionBody noOfParticipants;
//    private StageOptionBody noOfWinners;
    private StageOptionTime dateSpan;
    private StageOptionTime timeSpan;
    private LinearLayout options;


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

        options = new LinearLayout(context);
        options.setOrientation(VERTICAL);

        spinner = findViewById(R.id.stageoption_spinner);
        setOptions(context);

        name = new StageOptionBody(context, "Name of Stage");
        this.addView(name);

        noOfParticipants = new StageOptionBody(context, "Number of Participants");
        this.addView(noOfParticipants);

        dateSpan = new StageOptionTime(context, "Date");
        this.addView(dateSpan);

        timeSpan = new StageOptionTime(context, "Time");
        this.addView(timeSpan);

        this.addView(options);


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

        Map<String, Integer> options = returnOptions(type);
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

    public void setOptions(Context context) {
        options.removeAllViews();
        StageOptionBody bestOf = new StageOptionBody(context, "Best of");
        StageOptionBody pools = new StageOptionBody(context, "Pools");
        StageOptionBody numberOfRounds = new StageOptionBody(context, "Number of   Rounds");
        StageOptionBody numberOfWinners = new StageOptionBody(context, "Number of Winners");

        String selected = spinner.getSelectedItem().toString();
        if (selected.equals("Pools")) {
            options.addView(bestOf, 0);
            options.addView(numberOfRounds, 1);
            options.addView(pools, 2);
            options.addView(numberOfWinners, 3);

        } else if (selected.equals("Swiss Elim")) {
            options.addView(bestOf, 0);

        } else {
            options.addView(bestOf, 0);
            options.addView(numberOfRounds, 1);

        }
    }

    public HashMap<String, Integer> returnOptions(TournamentModel.StageTypes type) {
        HashMap<String, Integer> optionsMap = new HashMap<>();
        if (type == TournamentModel.StageTypes.POOLS) {
            optionsMap.put("bestOf", getOptionValue(0));
            optionsMap.put("numberOfRounds", getOptionValue(1));
            optionsMap.put("poolSize", getOptionValue(2));
            optionsMap.put("numberOfWinners", getOptionValue(3));

        } else if (type == TournamentModel.StageTypes.SWISS_ELIMINATION) {
            optionsMap.put("bestOf", getOptionValue(0));

        } else {
            optionsMap.put("bestOf", getOptionValue(0));
            optionsMap.put("numberOfRounds", getOptionValue(1));

        }
        return optionsMap;
    }

    private int getOptionValue(int index) {
        return Integer.parseInt(((StageOptionBody)this.options.getChildAt(index)).getEntry());
    }

    public void setNoOfParticipants(int number) {
        noOfParticipants.setEntryText(String.valueOf(number));
        noOfParticipants.setEnabled(false);
    }

}
