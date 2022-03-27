package co.tournam.api;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;

public class TournamentHandler {
    public static TournamentModel fromJSON(JSONObject tournament) throws JSONException {
        List<String> teams = new ArrayList<>();
        JSONArray teamsData = tournament.getJSONArray("teams");

        for(int i = 0; i < teamsData.length(); i++) {
            teams.add(teamsData.getString(i));
        }

        List<StageModel> stages = new ArrayList<>();
        JSONArray stagesData = tournament.getJSONArray("stages");

        for(int i = 0; i < teamsData.length(); i++) {
            stages.add(StageHandler.fromJSON(stagesData.getString(i)));
        }

        return new TournamentModel(
                tournament.getString("id"),
                tournament.getString("name"),
                tournament.getString("game"),
                teams,
                stages,
                UserHandler.fromJSON(tournament.getJSONObject("organizer")),
                tournament.getBoolean("isPublic"),
                tournament.getInt("color"),
                tournament.getInt("teamSize"),
                tournament.getInt("gameLength"),
                tournament.getInt("currentStage"),
                tournament.getString("banner"),
                tournament.getString("location"),
                tournament.getBoolean("online")
        );
    }

    public static void info(String tournamentId, InfoComplete listener) {
        RequestHandler.request("/tournament/info", Request.Method.GET, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                TournamentModel tournament = TournamentHandler.fromJSON(response.getJSONObject("tournament"));
                listener.success(tournament);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    

    public interface InfoComplete extends RequestHandler.AbstractCompleted {
        void success(TournamentModel tournament);
    }
}
