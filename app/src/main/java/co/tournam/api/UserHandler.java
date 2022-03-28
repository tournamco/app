package co.tournam.api;

import com.android.volley.Request;
import com.android.volley.Response;

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
                UserModel user = new UserModel(
                    response.getString("id"),
                    response.getString("username"),
                    response.getString("gamerTag"),
                    response.getString("email"),
                    response.getString("icon")
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

    public static boolean isLoggedIn() {
        return RequestHandler.hasCookie("_session");
    }
}
