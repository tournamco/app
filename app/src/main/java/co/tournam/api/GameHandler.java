package co.tournam.api;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import co.tournam.models.GameModel;

public class GameHandler {
    public static GameModel fromJSON(JSONObject game) throws JSONException {
        Map<String, Integer> scores = new ArrayMap<>();
        Map<String, String> proofs = new ArrayMap<>();

        JSONObject scoresData = game.getJSONObject("scores");
        JSONObject proofsData = game.getJSONObject("proofs");

        for (Iterator<String> it = scoresData.keys(); it.hasNext(); ) {
            String key = it.next();

            scores.put(key, scoresData.getInt(key));
            proofs.put(key, proofsData.getString(key));
        }

        return new GameModel(scores, proofs);
    }
}
