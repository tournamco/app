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
                user.getString("gamertag"),
                user.getString("email"),
                user.getString("icon")
        );
    }

    public static void create(String icon, String username, String password, String gamerTag, String email, CreateCompleted listener) {
        RequestHandler.request("/user/create", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);
                json.put("gamertag", gamerTag);
                json.put("email", email);
                json.put("icon", icon);

                return json;
            }, response -> listener.success(response.getString("id")));
    }

    public interface CreateCompleted {
        void success(String userId);
    }

    public static void login(String username, String password, LoginCompleted listener) {
        RequestHandler.request("/user/login", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);

                return json;
            }, response -> listener.success(response.getString("id")));
    }

    public interface LoginCompleted {
        void success(String userId);
    }

    public static void logout(LogoutCompleted listener) {
        RequestHandler.request("/user/logout", Request.Method.POST, null,
                response -> listener.success());
    }

    public interface LogoutCompleted {
        void success();
    }

    public static void me(MeCompleted listener) {
        RequestHandler.request("/user/me", Request.Method.POST, null, response -> {
            JSONObject userData = response.getJSONObject("user");
                UserModel user = new UserModel(
                        userData.getString("id"),
                        userData.getString("username"),
                        userData.getString("gamertag"),
                        userData.getString("email"),
                        userData.getString("icon")
                );

                listener.success(user);
            });
    }

    public interface MeCompleted  {
        void success(UserModel me);
    }

    public static void changeUsername(String newUsername, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "username");
                json.put("value", newUsername);

                return json;
            }, response -> listener.success());
    }

    public static void changeEmail(String newEmail, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "email");
                json.put("value", newEmail);

                return json;
            }, response -> listener.success());
    }

    public static void changeGamertag(String newGamertag, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("field", "gamertag");
                json.put("value", newGamertag);

                return json;
            }, response -> listener.success());
    }

    public static void changeIcon(String newIcon, ChangeComplete listener) {
        RequestHandler.request("/user/change", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("field", "icon");
                json.put("value", newIcon);

                return json;
            }, response -> listener.success());
    }

    public static void changePassword(String oldPassword, String newPassword, ChangeComplete listener) {
        RequestHandler.request("/user/changePassword", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("oldPassword", oldPassword);
                json.put("newPassword", newPassword);

                return json;
            }, response -> listener.success());
    }

    public interface ChangeComplete {
        void success();
    }

    public static boolean isLoggedIn() {
        return RequestHandler.hasCookie("_session");
    }
}
