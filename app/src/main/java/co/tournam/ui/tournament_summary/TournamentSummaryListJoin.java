package co.tournam.ui.tournament_summary;

import android.content.Context;

import java.util.List;

import co.tournam.models.TournamentModel;

public class TournamentSummaryListJoin extends AbstractTournamentSummary{

    private Context context;

    public TournamentSummaryListJoin(Context context, List<TournamentModel> tournaments) {
        super(context, tournaments);

        this.context = context;

        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {
        for(TournamentModel tournament : this.tournaments) {
            TournamentSummaryListJoinItem item = new TournamentSummaryListJoinItem(context, tournament);

            this.addView(item);
        }
    }


    public void addTournaments(List<TournamentModel> Newtournaments) {
        for(TournamentModel tournament : Newtournaments) {
            TournamentSummaryListJoinItem item = new TournamentSummaryListJoinItem(this.context, tournament);

            this.addView(item);
        }

    }

    public void clearList() {
        this.removeAllViews();
    }


}
