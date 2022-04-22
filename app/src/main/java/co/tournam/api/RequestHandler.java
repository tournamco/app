package co.tournam.api;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler {
    public final static String url = "https://api.tournam.co/";
    private static Cache cache = new NoCache();
    private static Network network = new BasicNetwork(new HurlStack());
    private static RequestQueue queue = new RequestQueue(cache, network);

    static {
        queue.start();
    }

    /**
     * Request a specified path from the API
     * @param path The path to request
     * @param method The method to use
     * @param bodyListener The listener to call to get the body object to send to the API
     * @param successListener The listener to call when the request is successful
     */
    public static void request(String path, int method, RequestBody bodyListener, RequestSuccess successListener) {
        JSONObject body = null;
        if(bodyListener != null) {
            try {
                body = bodyListener.body();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JsonObjectRequest request = new JsonObjectRequest(method, url + path, body, (Response.Listener<JSONObject>) response -> {
            try {
                successListener.success(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            NetworkResponse networkResponse = error.networkResponse;

            if(networkResponse == null || networkResponse.data == null) return;

            try {
                JSONObject response = new JSONObject(new String(networkResponse.data));
                if(!response.has("errno")) return;
                System.err.println("API_ERROR: "
                        + ApiErrors.valueOfIndex(response.getInt("errno")) + " - "
                        + response.getString("message"));
            } catch (JSONException e) {}
        });
        queue.add(request);
    }

    /**
     * Check if a certain cookie exists
     * @param name The name of the cookie
     * @return Whether the cookie exists
     */
    public static boolean hasCookie(String name) {
        try {
            List<HttpCookie> cookies = ((PersistentCookieStore) ((CookieManager)CookieHandler.getDefault()).getCookieStore()).get(new URI(url));

            for(HttpCookie cookie : cookies) {
                if(cookie.getName().equals(name) || cookie.getValue() == null) continue;

                return true;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Interface to get the body for a request
     */
    public interface RequestBody {
        JSONObject body() throws JSONException;
    }

    /**
     * Interface to get the response from a request
     */
    public interface RequestSuccess {
        void success(JSONObject response) throws JSONException;
    }
}
