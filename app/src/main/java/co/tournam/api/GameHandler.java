package co.tournam.api;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import co.tournam.models.GameModel;

public class GameHandler {
    /**
     * Parses a JSONObject into a GameModel
     * @param game JSONObject to parse
     * @return The GameModel
     * @throws JSONException
     */
    public static GameModel fromJSON(JSONObject game) throws JSONException {
        Map<String, Integer> scores = new ArrayMap<>();
        Map<String, String> proofs = new ArrayMap<>();

        JSONObject scoresData = game.getJSONObject("scores");
        JSONObject proofsData = game.getJSONObject("proofs");

        for (Iterator<String> it = scoresData.keys(); it.hasNext(); ) {
            String key = it.next();

            if(!scoresData.isNull(key)) {
                scores.put(key, scoresData.getInt(key));
            }
            else {
                scores.put(key, 0);
            }

            if(!proofsData.isNull(key)) {
                proofs.put(key, proofsData.getString(key));
            }
            else {
                proofs.put(key, null);
            }
        }

        return new GameModel(scores, proofs);
    }
}
