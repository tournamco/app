package co.tournam.ui.tournamentpage;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;

public class AbstractTournamentPage extends LinearLayout {

    protected TournamentModel tournament;

    public AbstractTournamentPage(Context context, TournamentModel tournament) {
        super(context);

        this.tournament = tournament;

        build(context);
    }

    private void build(Context context) { setOrientation(LinearLayout.VERTICAL);}
}
