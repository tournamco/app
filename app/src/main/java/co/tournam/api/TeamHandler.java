package co.tournam.api;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;

public class TeamHandler {
    /**
     * Parse the JSON response from the API and return the team model
     * @param team The JSONObject to parse
     * @return The team model
     * @throws JSONException if the JSONObject is invalid
     */
    public static TeamModel fromJSON(JSONObject team) throws JSONException {
        List<UserModel> members = new ArrayList<>();
        JSONArray membersData = team.getJSONArray("members");
        UserModel leader = null;

        for(int i = 0; i < membersData.length(); i++) {
            if(membersData.isNull(i)) continue;

            members.add(UserHandler.fromJSON(membersData.getJSONObject(i)));
        }

        if(!team.isNull("leader")) {
            leader = UserHandler.fromJSON(team.getJSONObject("leader"));
        }

        return new TeamModel(
                team.getString("id"),
                team.getString("name"),
                leader,
                members,
                team.getBoolean("isPublic"),
                team.getString("icon"),
                team.getString("tournament")
        );
    }

    /**
     * Create an empty team for the specified tournament
     * @param tournamentId The tournament id
     * @param isPublic Whether the team is public or not
     * @param listener The listener to call when the team is created
     */
    public static void createEmptyTeam(String tournamentId, boolean isPublic, CreateEmptyTeamComplete listener) {
        RequestHandler.request("/team/create", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("isPublic", isPublic);

                return json;
            }, response -> {
                String teamId = response.getString("id");
                String inviteToken = response.getString("token");

                listener.success(teamId, inviteToken);
            });
    }

    /**
     * Interface for the create empty team API call
     */
    public interface CreateEmptyTeamComplete {
        void success(String teamId, String inviteToken);
    }

    /**
     * API call to create a normal team
     * @param tournamentId The tournament id
     * @param join If the logged in user should join the team
     * @param iconId The icon id
     * @param isPublic Whether the team is public or not
     * @param name The name of the team
     * @param listener The listener to call when the team is created
     */
    public static void createNormalTeam(String tournamentId, boolean join, String iconId, boolean isPublic, String name, CreateNormalTeamComplete listener) {
        RequestHandler.request("/team/create", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("isPublic", isPublic);
                json.put("icon", iconId);
                json.put("name", name);
                json.put("join", join);

                return json;
            }, response -> {
                String teamId = response.getString("id");

                listener.success(teamId);
            });
    }

    /**
     * Interface for the create normal team API call
     */
    public interface CreateNormalTeamComplete {
        void success(String teamId);
    }

    /**
     * API call to join a team with an invite token
     * @param token The invite token
     * @param listener The listener to call when the team is joined
     */
    public static void joinByToken(String token, JoinCompleted listener) {
        RequestHandler.request("/team/join", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("token", token);

                return json;
            }, response -> {
                String teamId = response.getString("id");
                boolean isLeader = response.getBoolean("leader");

                listener.success(teamId, isLeader);
            });
    }

    /**
     * API call to join the specified team
     * @param teamId The team id
     * @param listener The listener to call when the team is joined
     */
    public static void joinByTeamId(String teamId, JoinCompleted listener) {
        RequestHandler.request("/team/join", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("team", teamId);

                return json;
            }, response -> {
                String id = response.getString("id");
                boolean isLeader = response.getBoolean("leader");

                listener.success(id, isLeader);
            });
    }

    /**
     * Interface for the join by token API call
     */
    public interface JoinCompleted {
        void success(String teamId, boolean isLeader);
    }

    /**
     * API call to create a team invite
     * @param tournamentId The tournament id
     * @param teamId The team id
     * @param listener The listener to call when the invite is created
     */
    public static void createInvite(String tournamentId, String teamId, CreateInviteComplete listener) {
        RequestHandler.request("/team/invite/create", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);
                json.put("team", teamId);

                return json;
            }, response -> listener.success(response.getString("token")));
    }

    /**
     * Interface for the create invite API call
     */
    public interface CreateInviteComplete {
        void success(String token);
    }

    /**
     * API call to finish a match
     * @param matchId The match id
     * @param listener The listener to call when the match is finished
     */
    public static void finishMatch(String matchId, FinishMatchComplete listener) {
        RequestHandler.request("/team/match/finish", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("match", matchId);

                return json;
            }, response -> listener.success());
    }

    /**
     * Interface for the finish match API call
     */
    public interface FinishMatchComplete {
        void success();
    }

    /**
     * API call to list the matches of the tournaments a user is in
     * @param future Whether to list future matches or not
     * @param personal Whether to list personal matches or not
     * @param pageNumber The page number
     * @param pageSize The page size
     * @param listener The listener to call when the matches are listed
     */
    public static void listMatches(boolean future, boolean personal, int pageNumber, int pageSize, ListMatchesComplete listener) {
        RequestHandler.request("/team/match/list", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("pageNumber", pageNumber);
                json.put("pageSize", pageSize);
                json.put("future", future);
                json.put("personal", personal);

                return json;
            }, response -> {
                JSONArray matchesData = response.getJSONArray("matches");
                List<MatchModel> matches = new ArrayList<>();

                for(int i = 0; i < matchesData.length(); i++) {
                    matches.add(MatchHandler.fromJSON(matchesData.getJSONObject(i)));
                }

                listener.success(matches);
            });
    }

    /**
     * Interface for the list matches API call
     */
    public interface ListMatchesComplete {
        void success(List<MatchModel> matches);
    }

    /**
     * API call to list the tournaments a user is in
     * @param pageNumber The page number
     * @param pageSize The page size
     * @param listener The listener to call when the tournaments are listed
     */
    public static void listTournaments(int pageNumber, int pageSize, ListTournamentsComplete listener) {
        RequestHandler.request("/team/tournament/list", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("pageNumber", pageNumber);
                json.put("pageSize", pageSize);

                return json;
            }, response -> {
                JSONArray tournamentsData = response.getJSONArray("tournaments");
                List<TournamentModel> tournaments = new ArrayList<>();

                for(int i = 0; i < tournamentsData.length(); i++) {
                    tournaments.add(TournamentHandler.fromJSON(tournamentsData.getJSONObject(i)));
                }

                listener.success(tournaments);
            });
    }

    /**
     * Interface for the list tournaments API call
     */
    public interface ListTournamentsComplete {
        void success(List<TournamentModel> tournaments);
    }

    public static void matchInfo(String matchId, MatchInfoComplete listener) {
        RequestHandler.request("/team/match/info", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("match", matchId);

                return json;
            }, response -> listener.success(MatchHandler.fromJSON(response.getJSONObject("match"))));
    }

    public interface MatchInfoComplete {
        void success(MatchModel match);
    }

    public static void info(String id, InfoComplete listener) {
        RequestHandler.request("/team/info", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("id", id);

                return json;
            }, response -> listener.success(TeamHandler.fromJSON(response.getJSONObject("team"))));
    }

    public interface InfoComplete {
        void success(TeamModel team);
    }

    public static void info(List<String> ids, InfoArrayComplete listener) {
        RequestHandler.request("/team/info", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                JSONArray array = new JSONArray();

                for(String id : ids) {
                   array.put(id);
                }
                json.put("id", array);

                return json;
            }, response -> {
                JSONArray teamsData = response.getJSONArray("teams");
                List<TeamModel> teams = new ArrayList<>();

                for(int i = 0; i < teamsData.length(); i++) {
                    teams.add(TeamHandler.fromJSON(teamsData.getJSONObject(i)));
                }

                listener.success(teams);
            });
    }

    public interface InfoArrayComplete {
        void success(List<TeamModel> teams);
    }

    public static void leaveSelf(String teamId, LeaveComplete listener) {
        RequestHandler.request("/team/leave", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("id", teamId);

                return json;
            }, response -> listener.success());
    }

    public static void leave(String teamId, String userId, LeaveComplete listener) {
        RequestHandler.request("/team/leave", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("id", teamId);
                json.put("user", userId);

                return json;
            }, response -> listener.success());
    }

    public interface LeaveComplete {
        void success();
    }

    public static void list(String tournamentId, ListComplete listener) {
        RequestHandler.request("/team/list", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);

                return json;
            }, response -> {
                JSONArray teamsData = response.getJSONArray("tournaments");
                List<TeamModel> teams = new ArrayList<>();

                for(int i = 0; i < teamsData.length(); i++) {
                    teams.add(TeamHandler.fromJSON(teamsData.getJSONObject(i)));
                }

                listener.success(teams);
            });
    }

    public interface ListComplete {
        void success(List<TeamModel> teams);
    }

    public static void resign(String teamId, String matchId, ResignComplete listener) {
        RequestHandler.request("/team/match/resign", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("team", teamId);
                json.put("match", matchId);

                return json;
            }, response -> listener.success());
    }

    public interface ResignComplete {
        void success();
    }

    public static void changeName(String newName, ChangeComplete listener) {
        RequestHandler.request("/team/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "name");
                json.put("value", newName);

                return json;
            }, response -> listener.success());
    }

    public static void changeIcon(String newIcon, ChangeComplete listener) {
        RequestHandler.request("/team/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "icon");
                json.put("value", newIcon);

                return json;
            }, response -> listener.success());
    }

    public static void changeIsPublic(boolean isPublic, ChangeComplete listener) {
        RequestHandler.request("/team/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "isPublic");
                json.put("value", isPublic);

                return json;
            }, response -> listener.success());
    }

    public interface ChangeComplete {
        void success();
    }

    public static void delete(String teamId, DeleteComplete listener) {
        RequestHandler.request("/team/delete", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("id", teamId);

                return json;
            }, response -> listener.success());
    }

    public interface DeleteComplete {
        void success();
    }
}
