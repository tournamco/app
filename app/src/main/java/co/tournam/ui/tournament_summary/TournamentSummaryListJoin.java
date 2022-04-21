package co.tournam.ui.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.TournamentModel;

public class TournamentSummaryListJoin extends AbstractTournamentSummary {

    private Context context;

    /**
     * Constructor for TournamentSummaryListJoin.
     *
     * @param context     the current context
     * @param tournaments the tournamentModel
     */
    public TournamentSummaryListJoin(Context context, List<TournamentModel> tournaments) {
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
        for(TournamentModel tournament : this.tournaments) {
            TournamentSummaryListJoinItem item = new TournamentSummaryListJoinItem(context, tournament, null);

            this.addView(item);
        }
    }

    /**
     * Function to add tournaments
     *
     * @param Newtournaments List of tournamentModels to add
     * @param location       the location of a tournament.
     */
    public void addTournaments(List<TournamentModel> Newtournaments, TournamentModel.TournamentLocation location) {
        for(TournamentModel tournament : Newtournaments) {
            TournamentSummaryListJoinItem item = new TournamentSummaryListJoinItem(this.context, tournament, location);

            this.addView(item);
        }

    }

    /**
     * Function to clear the tournament list.
     */
    public void clearList() {
        this.removeAllViews();
    }


}
