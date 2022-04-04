package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

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

public class TournamentActivity extends AppCompatActivity {

    private Context context;
    private FilterDropdown filter;
    private Slider slider;
    private DefaultButtonFilled button;
    private LinearLayout tournamentsLayout;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        context = this.getApplicationContext();
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
        tournamentsLayout = (LinearLayout) findViewById(R.id.tournamentsListing);
        tournamentsLayout.addView(list);
    }
}
