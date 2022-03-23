package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;
import co.tournam.schedule.R;
public class PoolStageOption extends LinearLayout {

    private Spinner spinner;

    public PoolStageOption(Context context) {
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
        spinner.setSelection(0);

        PoolStageOptionBody noOfParticipants = new PoolStageOptionBody(context, "Number of Participants");
        this.addView(noOfParticipants);

        PoolStageOptionBody poolSize = new PoolStageOptionBody(context, "Pool Size");
        this.addView(poolSize);

        PoolStageOptionBody noOfWinners = new PoolStageOptionBody(context, "Number of Winners");
        this.addView(noOfWinners);

        PoolStageOptionTime dateDuration = new PoolStageOptionTime(context, "Date");
        this.addView(dateDuration);

        PoolStageOptionTime timeDuration = new PoolStageOptionTime(context, "Time");
        this.addView(timeDuration);

    }
}
