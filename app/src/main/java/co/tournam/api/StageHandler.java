package co.tournam.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

        Date maximalDate = null;
        Date minimalDate = null;

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime maximalTime = LocalTime.parse(json.getString("maximalTime"), timeFormatter);
        LocalTime minimalTime = LocalTime.parse(json.getString("minimalTime"), timeFormatter);

        try {
            maximalDate = dateFormatter.parse(json.getString("maximalDate"));
            minimalDate = dateFormatter.parse(json.getString("minimalDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new StageModel(
                json.getString("type"),
                json.getString("name"),
                json.getInt("numberOfParticipants"),
                rounds,
                winners,
                minimalDate,
                maximalDate,
                minimalTime,
                maximalTime
        );
    }
}
