package co.tournam.ui.roundbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.models.RoundModel;
import co.tournam.schedule.R;

public class RoundBarItem extends LinearLayout {

    private RoundModel round;
    public Button button;

    public RoundBarItem(Context context, RoundModel round) {
        super(context);

        this.round = round;
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.round_header_item, this, true);

        button = findViewById(R.id.roundButton);
        button.setText(round.getName());
    }
}
