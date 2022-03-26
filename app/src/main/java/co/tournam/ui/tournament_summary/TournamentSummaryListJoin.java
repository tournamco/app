package co.tournam.ui.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.TournamentModel;

public class TournamentSummaryListJoin extends AbstractTournamentSummary{

    public TournamentSummaryListJoin(Context context, List<TournamentModel> tournaments) {
        super(context, tournaments);

        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {
        for(TournamentModel tournament : this.tournaments) {
            TournamentSummaryListJoinItem item = new TournamentSummaryListJoinItem(context, tournament);

            this.addView(item);
        }
    }
}
