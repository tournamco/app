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
    private String matchId;
    private String teamId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        if(b != null) {
            matchId = b.getString("key");
            teamId = b.getString("team");
        }
        else {
            return;
        }

        setContentView(R.layout.activity_match_proof);

        TeamHandler.matchInfo(matchId, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel response) {
                createProofsFromMatch(response);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                Toast.makeText(MatchProofActivity.this, "Error initializing activity:" + message, Toast.LENGTH_SHORT).show();
            }
        });

        context = this.getApplicationContext();
    }

    public void createProofsFromMatch(MatchModel match) {
        List<String> proofIds = getProofIdsFromMatch(match);


    }

    public List<String> getProofIdsFromMatch(MatchModel match) {
        List<String> proofIds = new ArrayList<>();

        for(GameModel game : match.getGames()) {
            proofIds.add(game.getProofs().get(teamId));
        }

        return proofIds;
    }


}
