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
            stages.add(StageHandler.fromJSON(stagesData.getJSONObject(i)));
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

    public static void create(JSONObject json, String name, int color, String game, int teamSize, boolean isPublic, int gameLength, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        RequestHandler.request("/tournament/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                json.put("name", name);
                json.put("game", game);
                json.put("color", color);
                json.put("teamSize", teamSize);
                json.put("isPublic", isPublic);
                json.put("gameLength", gameLength);
                json.put("online", true);
                JSONArray stagesData = new JSONArray();

                for(TournamentModel.CreateStageModel stage : stages) {
                    JSONObject stageData = new JSONObject();
                    stageData.put("type", stage.getType().getValue());
                    stageData.put("name", stage.getName());
                    stageData.put("numberOfParticipants", stage.getNumberOfParticipants());
                    stageData.put("minimalDate", stage.getMinimalDate());
                    stageData.put("maximalDate", stage.getMaximalDate());
                    stageData.put("minimalTime", stage.getMinimalTime());
                    stageData.put("maximalTime", stage.getMaximalTime());
                    JSONObject options = new JSONObject();

                    for(String key : stage.getOptions().keySet()) {
                        options.put(key, stage.getOptions().get(key));
                    }

                    stageData.put("options", options);
                    stagesData.put(stageData);
                }

                json.put("stages", stagesData);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                listener.success(response.getString("tournament"));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public static void createOnline(String name, int color, String game, int teamSize, boolean isPublic, int gameLength, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("online", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        create(json, name, color, game, teamSize, isPublic, gameLength, stages, listener);
    }

    public static void createOffline(String name, int color, String game, int teamSize, boolean isPublic, int gameLength, String location, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("online", false);
            json.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        create(json, name, color, game, teamSize, isPublic, gameLength, stages, listener);
    }

    public interface CreateComplete extends RequestHandler.AbstractCompleted {
        void success(String id);
    }

    public static void create(String id, DeleteComplete listener) {
        RequestHandler.request("/tournament/delete", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("id", id);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                listener.success();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface DeleteComplete extends RequestHandler.AbstractCompleted {
        void success();
    }
}
