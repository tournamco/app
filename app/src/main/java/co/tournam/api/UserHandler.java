package co.tournam.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import co.tournam.models.UserModel;

public class UserHandler {
    private final static String USERID_NAME = "userid";

    public static void create(String username, String password, String gamerTag, String email, CreateCompleted listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("gamerTag", gamerTag);
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestHandler.request("/user/create", Request.Method.POST, json, (Response.Listener<JSONObject>) response -> {
            try {
                if(response.getInt("code") != 200) {
                    listener.failure(response.getString("message"));
                }
                else {
                    String userId = response.getString("id");

                    listener.success(userId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            listener.failure(error.getMessage());
        });
    }

    public interface CreateCompleted {
        void success(String userId);
        void failure(String message);
    }

    public static void login(String username, String password, LoginCompleted listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestHandler.request("/user/login", Request.Method.POST, json, (Response.Listener<JSONObject>) response -> {
            try {
                if(response.getInt("code") != 200) {
                    listener.failure(response.getString("message"));
                }
                else {
                    String userId = response.getString("id");

                    listener.success(userId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            listener.failure(error.getMessage());
        });
    }

    public interface LoginCompleted {
        void success(String userId);
        void failure(String message);
    }

    public static void logout(LogoutCompleted listener) {
        RequestHandler.request("/user/logout", Request.Method.POST, null, (Response.Listener<JSONObject>) response -> {
            try {
                if(response.getInt("code") != 200) {
                    listener.failure(response.getString("message"));
                }
                else {
                    listener.success();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            listener.failure(error.getMessage());
        });
    }

    public interface LogoutCompleted {
        void success();
        void failure(String message);
    }

    public static void me(MeCompleted listener) {
        RequestHandler.request("/user/me", Request.Method.POST, null, (Response.Listener<JSONObject>) response -> {
            try {
                if(response.getInt("code") != 200) {
                    listener.failure(response.getString("message"));
                }
                else {
                    UserModel user = new UserModel(
                        response.getString("id"),
                        response.getString("username"),
                        response.getString("gamerTag"),
                        response.getString("email"),
                        response.getString("icon")
                    );

                    listener.success(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            listener.failure(error.getMessage());
        });
    }

    public interface MeCompleted {
        void success(UserModel me);
        void failure(String message);
    }

    public static boolean isLoggedIn() {
        return RequestHandler.hasCookie("_session");
    }
}
