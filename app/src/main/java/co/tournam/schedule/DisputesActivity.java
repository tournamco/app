package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.DisputeHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.FakeData;
import co.tournam.models.TournamentModel;
import co.tournam.ui.dispute.Dispute;
import co.tournam.ui.gameproof.GameProof;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.textentry.Text;

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

        if(b != null) {
            tournamentId = b.getString("tournamentid");
        }
        else {
            return;
        }

        loadTournament(tournamentId);
    }

    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                DisputesActivity.this.tournament = tournament;
                loadDisputes();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void loadDisputes() {
        DisputeHandler.list(tournament.getId(), new DisputeHandler.ListComplete() {
            @Override
            public void success(List<DisputeModel> disputes) {
                DisputesActivity.this.disputes = disputes;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        LinearLayout headerLayout = findViewById(R.id.header);
        headerLayout.addView(new SmallHeader(context, "Tournament disputes", () -> finish()));

        setDisputes(disputes);
    }

    private void setDisputes(List<DisputeModel> disputes) {
        disputesLayout = (LinearLayout) findViewById(R.id.disputes_list);
        for(DisputeModel dispute : disputes) {
            disputesLayout.addView(new Dispute(context, dispute, d -> disputesLayout.removeView(d)));
        }
    }

}
