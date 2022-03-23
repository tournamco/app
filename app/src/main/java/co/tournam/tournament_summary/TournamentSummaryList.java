package co.tournam.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.models.TournamentModel;

public class TournamentSummaryList extends AbstractTournamentSummary {


    public TournamentSummaryList(Context context, List<TournamentModel> tournaments) {
        super(context, tournaments);

        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {
        for(TournamentModel tournament : this.tournaments) {
            TournamentSummaryListItem item = new TournamentSummaryListItem(context, tournament);

            this.addView(item);
        }
    }
}
