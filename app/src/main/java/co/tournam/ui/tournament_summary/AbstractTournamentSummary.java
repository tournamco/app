package co.tournam.ui.tournament_summary;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;

public abstract class AbstractTournamentSummary extends LinearLayout {

    protected List<TournamentModel> tournaments;

    /**
     * The constructor for the AbstractTournamentSummary class.
     *
     * @param context     the current context
     * @param tournaments the list of tournamentModels.
     */
    public AbstractTournamentSummary(Context context, List<TournamentModel> tournaments) {
        super(context);

        this.tournaments = tournaments;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
