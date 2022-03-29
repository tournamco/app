package co.tournam.schedule;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerTournament;
import co.tournam.ui.tournament_summary.TournamentSummaryList;

public class TournamentActivity extends LinearLayout {

    private FilterDropdown filter;
    private Slider slider;
    private DefaultButtonFilled button;

    public TournamentActivity(Context context) {

        super(context);

        build(context);
    }

    public void build(Context context) {

        setOrientation(VERTICAL);


        buildContents(context);
    }

    public void buildContents(Context context) {
        headerTournament header = new headerTournament(this.getContext(), "Tournaments");
        this.addView(header);

        TeamHandler.listTournaments(0, 10, new TeamHandler.ListTournamentsComplete() {
            @Override
            public void success(List<TournamentModel> matches) {
                TournamentActivity.this.buildTournamentSummaryList(matches, context);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void buildTournamentSummaryList(List<TournamentModel> tournaments, Context context) {
        TournamentSummaryList list = new TournamentSummaryList(context, tournaments);
        this.addView(list);
    }
}
