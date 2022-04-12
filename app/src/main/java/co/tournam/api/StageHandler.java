package co.tournam.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.RoundModel;
import co.tournam.models.stage.StageModel;

public class StageHandler {
    public static StageModel fromJSON(JSONObject json) throws JSONException {
        List<RoundModel> rounds = new ArrayList<>();
        JSONArray roundsData = json.getJSONArray("rounds");

        for(int i = 0; i < roundsData.length(); i++) {
            rounds.add(RoundHandler.fromJSON(roundsData.getJSONObject(i)));
        }

        List<String> winners = new ArrayList<>();
        JSONArray winnersData = json.getJSONArray("winners");

        for(int i = 0; i < winnersData.length(); i++) {
            winners.add(winnersData.getString(i));
        }

        return new StageModel(
                json.getString("type"),
                json.getString("name"),
                json.getInt("numberOfParticipants"),
                rounds,
                winners
        );
    }
}
