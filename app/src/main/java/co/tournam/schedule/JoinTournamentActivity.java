package co.tournam.schedule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.core.operation.ListenComplete;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.FakeData;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

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
