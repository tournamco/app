package co.tournam.ui.roundbar;

import android.content.Context;

import java.util.List;

import co.tournam.models.RoundModel;


public class RoundBar extends AbstractRoundBar {

    RoundbarActionListener listener;
    public RoundBar(Context context, List<RoundModel> rounds, RoundbarActionListener listener) {
        super(context, rounds);

        this.listener = listener;
        build(context);
    }

    public void build(Context context) {
        buildContents(context);
    }

    public void buildContents(Context context) {
        for (RoundModel round : this.rounds) {
            RoundBarItem item = new RoundBarItem(context, round, listener);
            this.addView(item);
        }
    }

    public interface RoundbarActionListener {
        void onRoundSelected(RoundModel round);
    }

}
