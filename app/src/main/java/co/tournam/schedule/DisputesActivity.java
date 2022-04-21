package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.DisputeHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.dispute.Dispute;
import co.tournam.ui.header.SmallHeader;

public class DisputesActivity extends AppCompatActivity {

    Context context;
    private LinearLayout disputesLayout;
    private List<DisputeModel> disputes;
    private TournamentModel tournament;

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

    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, tournament -> {
            DisputesActivity.this.tournament = tournament;
            loadDisputes();
        });
    }

    private void loadDisputes() {
        DisputeHandler.list(tournament.getId(), disputes -> {
            DisputesActivity.this.disputes = disputes;
            build();
        });
    }

    private void build() {
        LinearLayout headerLayout = findViewById(R.id.header);
        headerLayout.addView(new SmallHeader(context, "Tournament disputes", () -> finish()));

        setDisputes(disputes);
    }

    private void setDisputes(List<DisputeModel> disputes) {
        disputesLayout = (LinearLayout) findViewById(R.id.disputes_list);
        for (DisputeModel dispute : disputes) {
            disputesLayout.addView(new Dispute(context, dispute, d -> disputesLayout.removeView(d)));
        }
    }

}
