package co.tournam.api;

import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;

public class MatchHandler {
    public static MatchModel fromJSON(JSONObject match) throws JSONException {
        JSONObject tournamentData = match.getJSONObject("tournament");

        Map<String, Integer> scores = new ArrayMap<>();
        Map<String, TeamModel> teams = new ArrayMap<>();
        List<String> keys = new ArrayList<>();

        JSONArray keysData = match.getJSONArray("keys");
        JSONObject scoresData = match.getJSONObject("scores");
        JSONObject teamsData = match.getJSONObject("teams");

        for(int i = 0; i < keysData.length(); i++) {
            String key = keysData.getString(i);
            keys.add(key);
            scores.put(key, scoresData.getInt(key));
            if(teamsData.has(key)) {
                teams.put(key, TeamHandler.fromJSON(teamsData.getJSONObject(key)));
            }
            else {
                teams.put(key, null);
            }
        }

        List<GameModel> games = new ArrayList<>();
        JSONArray gamesData = match.getJSONArray("games");

        for(int i = 0; i < gamesData.length(); i++) {
            games.add(GameHandler.fromJSON(gamesData.getJSONObject(i)));
        }

        return new MatchModel(
                match.getString("id"),
                match.getString("name"),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(match.getLong("startDate")), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(match.getLong("endDate")), ZoneId.systemDefault()),
                new MatchModel.MatchTournamentModel(
                        tournamentData.getString("id"),
                        tournamentData.getString("name"),
                        tournamentData.getInt("color")
                ),
                scores, keys, games, teams
        );
    }
}
