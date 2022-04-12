package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;

public class JoinTournamentActivity extends AppCompatActivity {

    Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout teamsListLayout;
    private LinearLayout backButton;
    private TournamentModel tournament;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tournament);

        Bundle b = getIntent().getExtras();

        String tournamentID = null;
        if(b != null) {
            tournamentID = b.getString("tournamentid");
        }

        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                JoinTournamentActivity.this.tournament = tournament;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });

        context = this.getApplicationContext();
    }

    private void build() {

    }


}
