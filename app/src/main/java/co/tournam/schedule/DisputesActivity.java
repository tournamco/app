package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.DisputeModel;
import co.tournam.models.FakeData;
import co.tournam.models.TournamentModel;
import co.tournam.ui.dispute.Dispute;
import co.tournam.ui.gameproof.GameProof;

public class DisputesActivity extends AppCompatActivity {

    Context context;
    private LinearLayout disputesLayout;
    private TournamentModel model;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_disputes);
        context = this.getApplicationContext();
        Bundle b = getIntent().getExtras();
        String tournamentID = null;

        if(b != null) {
            tournamentID = b.getString("key");
        }

        TournamentHandler.info(tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                model = tournament;
            }

            @Override
            public void failure(ApiErrors error, String message) {

            }
        });
        List<DisputeModel> disputes = new ArrayList<>();
        disputes = model.getDisputes();
        setDisputes(disputes);
    }

    private void setDisputes(List<DisputeModel> disputes) {
        disputesLayout = (LinearLayout) findViewById(R.id.disputes);
        disputesLayout.addView(new Dispute(
                context,
                disputes
        ));

    }

}
