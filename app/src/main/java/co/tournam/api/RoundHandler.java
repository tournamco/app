package co.tournam.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.RoundModel;

public class RoundHandler {
    /**
     * Parse the JSON response from the API and return the round model
     * @param json The JSONObject to parse
     * @return The round model
     * @throws JSONException if the JSONObject is invalid
     */
    public static RoundModel fromJSON(JSONObject json) throws JSONException {
        List<String> matches = new ArrayList<>();
        JSONArray matchesData = json.getJSONArray("matches");

        for(int i = 0; i < matchesData.length(); i++) {
            matches.add(matchesData.getString(i));
        }

        return new RoundModel(json.getString("name"), matches);
    }
}
