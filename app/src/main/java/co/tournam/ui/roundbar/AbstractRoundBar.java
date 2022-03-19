package co.tournam.ui.roundbar;

import android.content.Context;
import android.widget.LinearLayout;
import java.util.List;
import co.tournam.models.RoundModel;

public abstract class AbstractRoundBar extends LinearLayout {

    protected List<RoundModel> rounds;

    public AbstractRoundBar(Context context, List<RoundModel> rounds) {
        super(context);

        this.rounds = rounds;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}
