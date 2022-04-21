package co.tournam.ui.roundbar;

import android.content.Context;

import java.util.List;

import co.tournam.models.RoundModel;


public class RoundBar extends AbstractRoundBar {

    RoundbarActionListener listener;

    /**
     * @param context  the current context
     * @param rounds   the list of roundModels
     * @param listener The listener for the roundbarActionListener
     */
    public RoundBar(Context context, List<RoundModel> rounds, RoundbarActionListener listener) {
        super(context, rounds);

        this.listener = listener;
        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {
        for (RoundModel round : this.rounds) {
            RoundBarItem item = new RoundBarItem(context, round, listener);
            this.addView(item);
        }
    }

    /**
     * the interface for the roundBarActionListener.
     */
    public interface RoundbarActionListener {
        void onRoundSelected(RoundModel round);
    }

}
