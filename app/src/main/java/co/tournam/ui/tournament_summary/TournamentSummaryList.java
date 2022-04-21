package co.tournam.ui.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.TournamentModel;

public class TournamentSummaryList extends AbstractTournamentSummary {

    private Context context;

    /**
     * The constructor for the TournamentSummaryList class.
     *
     * @param context     the current context
     * @param tournaments the list of tournamentModels.
     */
    public TournamentSummaryList(Context context, List<TournamentModel> tournaments) {
        super(context, tournaments);

        this.context = context;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        for (TournamentModel tournament : this.tournaments) {
            TournamentSummaryListItem item = new TournamentSummaryListItem(context, tournament);

            this.addView(item);
        }
    }

    /**
     * A function to add more tournaments.
     *
     * @param tournaments List of tournamentModels.
     */
    public void addTournaments(List<TournamentModel> tournaments) {
        this.tournaments.addAll(tournaments);

        for (TournamentModel tournament : tournaments) {
            TournamentSummaryListItem item = new TournamentSummaryListItem(context, tournament);

            this.addView(item);
        }
    }
}
