package co.tournam.ui.stageoptions;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
    private ParticipantsUpdateListener listener;

    public StageOption(Context context, ParticipantsUpdateListener listener) {
        super(context);

        this.listener = listener;

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

        name = new StageOptionBody(context, "Name of Stage", InputType.TYPE_CLASS_TEXT);
        this.addView(name);

        noOfParticipants = new StageOptionBody(context, "Number of Participants", InputType.TYPE_CLASS_NUMBER);
        this.addView(noOfParticipants);

        dateSpan = new StageOptionTime(context, "Date", InputType.TYPE_DATETIME_VARIATION_DATE);
        this.addView(dateSpan);

        timeSpan = new StageOptionTime(context, "Time", InputType.TYPE_DATETIME_VARIATION_TIME);
        this.addView(timeSpan);

        this.addView(options);
    }

    public int getNumberOfNextParticipants() {
        switch (spinner.getSelectedItem().toString()){
            case "Pools":
                return getOptionValue(3) * (int) Math.ceil((float)Integer.parseInt(this.noOfParticipants.getEntry()) / (float)getOptionValue(2));
            case "Single Elim":
                return (int) Math.ceil(Integer.parseInt(this.noOfParticipants.getEntry()) / Math.pow(2, getOptionValue(1)));
            case "Double Elim":
                int upperBracket = Integer.parseInt(this.noOfParticipants.getEntry());
                int lowerBracket = 0;

                if(getOptionValue(1) <= 1) return upperBracket;

                for(int i = 1; i < getOptionValue(1); i++) {
                    if(upperBracket == 1 && lowerBracket == 1) {
                        return 1;
                    }

                    float oldUpperBracket = (float)upperBracket;
                    upperBracket = (int) Math.ceil(oldUpperBracket / 2);
                    lowerBracket += Math.floor(oldUpperBracket / 2);
                    lowerBracket = (int) Math.ceil((float)lowerBracket / 2);
                }

                return lowerBracket + upperBracket;
            case "Swiss Elim":
                return (int) Math.ceil(Integer.parseInt(this.noOfParticipants.getEntry()) / 2);
        }

        return 0;
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
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                StageOption.this.listener.onParticipantsUpdate();
            }
        };

        StageOptionBody bestOf = new StageOptionBody(context, "Best of", InputType.TYPE_CLASS_NUMBER);
        StageOptionBody pools = new StageOptionBody(context, "Pool Size", InputType.TYPE_CLASS_NUMBER);
        StageOptionBody numberOfRounds = new StageOptionBody(context, "Number of Rounds", InputType.TYPE_CLASS_NUMBER);
        StageOptionBody numberOfWinners = new StageOptionBody(context, "Number of Winners", InputType.TYPE_CLASS_NUMBER);
        bestOf.entryText.addTextChangedListener(watcher);
        pools.entryText.addTextChangedListener(watcher);
        numberOfRounds.entryText.addTextChangedListener(watcher);
        numberOfWinners.entryText.addTextChangedListener(watcher);

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

    public void setOpenNoOfParticipants() {
        noOfParticipants.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        noOfParticipants.entryText.setEnabled(true);
    }

    public void setForcedNoOfParticipants(int number) {
        noOfParticipants.setEntryText(String.valueOf(number));
        noOfParticipants.setBackgroundColor(Color.parseColor("#FFF0F0F0"));
        noOfParticipants.entryText.setEnabled(false);
    }

    public void setErrorNoOfParticipants() {
        noOfParticipants.setEntryText(String.valueOf(0));
        noOfParticipants.setBackgroundColor(Color.parseColor("#FFFF0000"));
        noOfParticipants.entryText.setEnabled(false);
    }

    public interface ParticipantsUpdateListener {
        void onParticipantsUpdate();
    }
}
