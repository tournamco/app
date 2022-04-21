package co.tournam.ui.roundbar;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.RoundModel;

public abstract class AbstractRoundBar extends LinearLayout {

    protected List<RoundModel> rounds;

    /**
     * A constructor for the AbstractRoundBar.
     *
     * @param context the current context
     * @param rounds  the list of roundModels.
     */
    public AbstractRoundBar(Context context, List<RoundModel> rounds) {
        super(context);

        this.rounds = rounds;

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
    }
}
