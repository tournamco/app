package co.tournam.ui.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.TournamentModel;

public class TournamentSummaryList extends AbstractTournamentSummary {

    private Context context;

    public TournamentSummaryList(Context context, List<TournamentModel> tournaments) {
        super(context, tournaments);

        this.context = context;

        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {
        for(TournamentModel tournament : this.tournaments) {
            TournamentSummaryListItem item = new TournamentSummaryListItem(context, tournament);

            this.addView(item);
        }
    }

    public void addTournaments(List<TournamentModel> tournaments) {
        this.tournaments.addAll(tournaments);

        for(TournamentModel tournament : tournaments) {
            TournamentSummaryListItem item = new TournamentSummaryListItem(context, tournament);

            this.addView(item);
        }
    }
}
