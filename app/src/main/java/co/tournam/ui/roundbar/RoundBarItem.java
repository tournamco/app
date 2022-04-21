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
    private RoundBar.RoundbarActionListener listener;

    /**
     * The Constructor for the RoundBarItem.
     *
     * @param context  the current context
     * @param round    the roundModel
     * @param listener the listener for the RoundBarActionListener
     */
    public RoundBarItem(Context context, RoundModel round, RoundBar.RoundbarActionListener listener) {
        super(context);

        this.round = round;
        this.listener = listener;
        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.round_header_item, this, true);

        button = findViewById(R.id.roundButton);
        button.setText(round.getName());
        button.setOnClickListener(v -> {
            listener.onRoundSelected(round);
        });
    }
}
