package co.tournam.api;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.MatchModel;
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

        for(int i = 0; i < stagesData.length(); i++) {
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
        RequestHandler.request("/tournament/info", Request.Method.POST, new RequestHandler.RequestSetup() {
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

    public static void create(JSONObject json, String banner, String name, int color, String game, int teamSize, boolean isPublic, int gameLength, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        RequestHandler.request("/tournament/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                json.put("banner", banner);
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
                listener.success(response.getString("id"));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public static void createOnline(String banner, String name, int color, String game, int teamSize, boolean isPublic, int gameLength, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("online", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        create(json, banner, name, color, game, teamSize, isPublic, gameLength, stages, listener);
    }

    public static void createOffline(String banner, String name, int color, String game, int teamSize, boolean isPublic, int gameLength, String location, List<TournamentModel.CreateStageModel> stages, CreateComplete listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("online", false);
            json.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        create(json, banner, name, color, game, teamSize, isPublic, gameLength, stages, listener);
    }

    public interface CreateComplete extends RequestHandler.AbstractCompleted {
        void success(String id);
    }

    public static void delete(String id, DeleteComplete listener) {
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

    public static void listMatches(String tournamentId, ListMatchesComplete listener) {
        RequestHandler.request("/tournament/match/list", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                List<MatchModel> matches = new ArrayList<>();
                JSONArray matchesData = response.getJSONArray("matches");

                for(int i = 0; i < matchesData.length(); i++) {
                    matches.add(MatchHandler.fromJSON(matchesData.getJSONObject(i)));
                }

                listener.success(matches);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface ListMatchesComplete extends RequestHandler.AbstractCompleted {
        void success(List<MatchModel> matches);
    }

    public static void listRoundMatches(String tournamentId, int stageIndex, int roundIndex, ListRoundMatchesComplete listener) {
        RequestHandler.request("/tournament/round/list", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("stage", stageIndex);
                json.put("round", roundIndex);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                List<MatchModel> matches = new ArrayList<>();
                JSONArray matchesData = response.getJSONArray("matches");

                for(int i = 0; i < matchesData.length(); i++) {
                    matches.add(MatchHandler.fromJSON(matchesData.getJSONObject(i)));
                }

                listener.success(matches);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface ListRoundMatchesComplete extends RequestHandler.AbstractCompleted {
        void success(List<MatchModel> matches);
    }

    /**
     * Get the list of all tournaments that are at a location nearby.
     * @param location The location to search for tournaments.
     * @param distance The radius to search for tournaments in degrees.
     * @param listener The listener to call when the request is complete.
     */
    public static void discoveryLocal(String location, int distance, int pageNumber, int pageSize, DiscoverComplete listener) {
        RequestHandler.request("/tournament/discovery", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("location", location);
                json.put("distance", distance);
                json.put("local", true);
                json.put("pageNumber", pageNumber);
                json.put("pageSize", pageSize);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                JSONArray tournamentsData = response.getJSONArray("tournaments");
                List<TournamentModel> tournaments = new ArrayList<>();

                for(int i = 0; i < tournamentsData.length(); i++) {
                    tournaments.add(TournamentHandler.fromJSON(tournamentsData.getJSONObject(i)));
                }

                listener.success(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public static void discoveryOnline(int pageNumber, int pageSize, DiscoverComplete listener) {
        RequestHandler.request("/tournament/discovery", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("local", false);
                json.put("pageNumber", pageNumber);
                json.put("pageSize", pageSize);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                JSONArray tournamentsData = response.getJSONArray("tournaments");
                List<TournamentModel> tournaments = new ArrayList<>();

                for(int i = 0; i < tournamentsData.length(); i++) {
                    tournaments.add(TournamentHandler.fromJSON(tournamentsData.getJSONObject(i)));
                }

                listener.success(tournaments);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface DiscoverComplete extends RequestHandler.AbstractCompleted {
        void success(List<TournamentModel> tournaments);
    }
}
