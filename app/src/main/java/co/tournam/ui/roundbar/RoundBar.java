package co.tournam.ui.roundbar;

import android.content.Context;
import java.util.*;

import co.tournam.models.RoundModel;


public class RoundBar extends AbstractRoundBar {
    public RoundBar(Context context, ArrayList<RoundModel> rounds) {
        super(context, rounds);

        build(context);
    }

    public void build(Context context) {
        buildContents(context);
    }

    public void buildContents(Context context) {
        for (RoundModel round : this.rounds) {
            RoundBarItem item = new RoundBarItem(context, round);
            this.addView(item);
        }
    }

}
