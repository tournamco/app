package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.UserHandler;
import co.tournam.models.FakeData;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.ui.gameproof.GameProof;

public class MatchProofActivity extends AppCompatActivity {

    Context context;
    private LinearLayout matchProofLayout;
    private MatchModel match;
    private boolean inTeam;
    private String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String matchCode = null;

        if(b != null) {
            matchCode = b.getString("key");
        }
        setContentView(R.layout.activity_match_proof);
        TeamHandler.matchInfo(matchCode, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel response) {
                match = response;
            }

            @Override
            public void failure(ApiErrors error, String message) {
                Toast.makeText(MatchProofActivity.this, "Error initializing activity:" + message, Toast.LENGTH_SHORT).show();
            }
        });
        context = this.getApplicationContext();
        setDisputes();
    }

    private void setDisputes() {
        matchProofLayout = (LinearLayout) findViewById(R.id.proof);
        List<GameModel> games = match.getGames();
        inTeam = false;
        Map<String, TeamModel> teams = match.getTeams();
        for (TeamModel team : teams.values()) {
            UserHandler.isUserInTeam(team.getID(), new UserHandler.IsUserInTeamComplete() {
                @Override
                public void success(boolean isInTeam) {
                    inTeam = isInTeam;
                }

                @Override
                public void failure(ApiErrors error, String message) {

                }
            });
            if(inTeam == true) {
                id = team.getID();
                for (GameModel game : games) {
                    matchProofLayout.addView(new GameProof(context, game, match, id ));
                }
            }
        }
    }


}
