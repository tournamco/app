package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.tournam.api.DisputeHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.dispute.Dispute;
import co.tournam.ui.header.SmallHeader;

public class DisputesActivity extends AppCompatActivity {

    //Variable Declarations
    Context context;
    private LinearLayout disputesLayout;
    private List<DisputeModel> disputes;
    private TournamentModel tournament;

    //On create method of the Disputes Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_disputes);
        context = this.getApplicationContext();
        Bundle b = getIntent().getExtras();
        String tournamentId = null;

        if (b != null) {
            tournamentId = b.getString("tournamentid");
        } else {
            return;
        }

        loadTournament(tournamentId);
    }

    /**
     * Loads and sets the information of a tournament with its given ID.
     *
     * @param tournamentId id of the tournament
     * @post this.tournament = TournamentModel tournament
     */
    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                DisputesActivity.this.tournament = tournament;
                loadDisputes();
            }
        });
    }

    //Loads the disputes of the current tournament and sets them to the global variable
    private void loadDisputes() {
        DisputeHandler.list(tournament.getId(), new DisputeHandler.ListComplete() {
            @Override
            public void success(List<DisputeModel> disputes) {
                DisputesActivity.this.disputes = disputes;
                build();
            }
        });
    }

    //Builds the components of the activity
    private void build() {
        LinearLayout headerLayout = findViewById(R.id.header);
        headerLayout.addView(new SmallHeader(context, "Tournament disputes", () -> finish()));

        setDisputes(disputes);
    }

    //Displays and builds all the disputes from the global list
    private void setDisputes(List<DisputeModel> disputes) {
        disputesLayout = (LinearLayout) findViewById(R.id.disputes_list);
        for (DisputeModel dispute : disputes) {
            disputesLayout.addView(new Dispute(context, dispute, d -> disputesLayout.removeView(d)));
        }
    }

}
