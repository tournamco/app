package co.tournam.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.RoundModel;

public class RoundHandler {
    public static RoundModel fromJSON(JSONObject json) throws JSONException {
        List<String> matches = new ArrayList<>();
        JSONArray matchesData = json.getJSONArray("matches");

        for(int i = 0; i < matchesData.length(); i++) {
            matches.add(matchesData.getString(i));
        }

        return new RoundModel(json.getString("name"), matches);
    }
}
