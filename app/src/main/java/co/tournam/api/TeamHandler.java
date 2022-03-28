package co.tournam.api;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class TeamHandler {
    public static TeamModel fromJSON(JSONObject team) throws JSONException {
        List<UserModel> members = new ArrayList<>();
        JSONArray membersData = team.getJSONArray("members");

        for(int i = 0; i < membersData.length(); i++) {
            members.add(UserHandler.fromJSON(membersData.getJSONObject(i)));
        }

        return new TeamModel(
                team.getString("id"),
                team.getString("name"),
                UserHandler.fromJSON(team.getJSONObject("leader")),
                members,
                team.getBoolean("isPublic"),
                team.getString("icon"),
                team.getString("tournament")
        );
    }

    public static void createEmptyTeam(String tournamentId, boolean isPublic, CreateEmptyTeamComplete listener) {
        RequestHandler.request("/team/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("isPublic", isPublic);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String teamId = response.getString("id");
                String inviteToken = response.getString("token");

                listener.success(teamId, inviteToken);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface CreateEmptyTeamComplete extends RequestHandler.AbstractCompleted {
        void success(String teamId, String inviteToken);
    }

    public static void createNormalTeam(String tournamentId, boolean isPublic, String name, CreateNormalTeamComplete listener) {
        RequestHandler.request("/team/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("isPublic", isPublic);
                json.put("name", name);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String teamId = response.getString("id");

                listener.success(teamId);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface CreateNormalTeamComplete extends RequestHandler.AbstractCompleted {
        void success(String teamId);
    }

    public static void joinByToken(String token, JoinCompleted listener) {
        RequestHandler.request("/team/join", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("token", token);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String teamId = response.getString("id");
                boolean isLeader = response.getBoolean("leader");

                listener.success(teamId, isLeader);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public static void joinByTeamId(String teamId, JoinCompleted listener) {
        RequestHandler.request("/team/join", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("team", teamId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String teamId = response.getString("id");
                boolean isLeader = response.getBoolean("leader");

                listener.success(teamId, isLeader);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface JoinCompleted extends RequestHandler.AbstractCompleted {
        void success(String teamId, boolean isLeader);
    }

    public static void createInvite(String tournamentId, String teamId, CreateInviteComplete listener) {
        RequestHandler.request("/team/invite/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("team", teamId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String token = response.getString("token");

                listener.success(token);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface CreateInviteComplete extends RequestHandler.AbstractCompleted {
        void success(String token);
    }

    public static void finishMatch(String matchId, FinishMatchComplete listener) {
        RequestHandler.request("/team/match/finish", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("match", matchId);

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

    public interface FinishMatchComplete extends RequestHandler.AbstractCompleted {
        void success();
    }

    public static void listMatches(int pageNumber, int pageSize, ListMatchesComplete listener) {
        RequestHandler.request("/team/match/list", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("pageNumber", pageNumber);
                json.put("pageSize", pageSize);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                JSONArray matchesData = response.getJSONArray("matches");
                List<MatchModel> matches = new ArrayList<>();

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
}
