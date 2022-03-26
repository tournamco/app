package co.tournam.ui.tournament_summary;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;

public abstract class AbstractTournamentSummary extends LinearLayout {

    protected List<TournamentModel> tournaments;

    public AbstractTournamentSummary(Context context, List<TournamentModel> tournaments) {
        super(context);

        this.tournaments = tournaments;

        build(context);
    }

    private void build(Context context) { setOrientation(LinearLayout.VERTICAL);}
}
