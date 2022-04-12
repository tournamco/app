package co.tournam.api;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class UserHandler {
    public static UserModel fromJSON(JSONObject user) throws JSONException {
        return new UserModel(
                user.getString("id"),
                user.getString("username"),
                user.getString("email"),
                user.getString("gamertag"),
                user.getString("icon")
        );
    }

    public static void create(String username, String password, String gamerTag, String email, CreateCompleted listener) {
        RequestHandler.request("/user/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);
                json.put("gamerTag", gamerTag);
                json.put("email", email);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String userId = response.getString("id");

                listener.success(userId);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface CreateCompleted extends RequestHandler.AbstractCompleted {
        void success(String userId);
    }

    public static void login(String username, String password, LoginCompleted listener) {
        RequestHandler.request("/user/login", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String userId = response.getString("id");

                listener.success(userId);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface LoginCompleted extends RequestHandler.AbstractCompleted {
        void success(String userId);
    }

    public static void logout(LogoutCompleted listener) {
        RequestHandler.request("/user/logout", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                return null;
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

    public interface LogoutCompleted extends RequestHandler.AbstractCompleted {
        void success();
    }

    public static void me(MeCompleted listener) {
        RequestHandler.request("/user/me", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                return null;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                JSONObject userData = response.getJSONObject("user");
                UserModel user = new UserModel(
                        userData.getString("id"),
                        userData.getString("username"),
                        userData.getString("gamertag"),
                        userData.getString("email"),
                        userData.getString("icon")
                );

                listener.success(user);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface MeCompleted extends RequestHandler.AbstractCompleted {
        void success(UserModel me);
    }

    public static void changeUsername(String newUsername, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("field", "username");
                json.put("value", newUsername);

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

    public static void changeEmail(String newEmail, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("field", "email");
                json.put("value", newEmail);

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

    public static void changeGamertag(String newGamertag, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("field", "gamertag");
                json.put("value", newGamertag);

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

    public static void changeIcon(String newIcon, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("field", "icon");
                json.put("value", newIcon);

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

    public static void changePassword(String oldPassword, String newPassword, ChangeComplete listener) {
        RequestHandler.request("/user/changePassword", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("oldPassword", oldPassword);
                json.put("newPassword", newPassword);

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

    public interface ChangeComplete extends RequestHandler.AbstractCompleted {
        void success();
    }

    public static void isUserInTeam(String teamId, IsUserInTeamComplete listener) {
        TeamHandler.info(teamId, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                UserHandler.me(new MeCompleted() {
                    @Override
                    public void success(UserModel user) {
                        listener.success(team.getMembers().contains(user.getId()));
                    }

                    @Override
                    public void failure(ApiErrors error, String message) {
                        listener.failure(error, message);
                    }
                });
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface IsUserInTeamComplete extends RequestHandler.AbstractCompleted {
        void success(boolean isInTeam);
    }

    public static boolean isLoggedIn() {
        return RequestHandler.hasCookie("_session");
    }
}
