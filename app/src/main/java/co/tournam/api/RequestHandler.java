package co.tournam.api;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler {
    private static String url = "http://192.168.56.1:8183";
    private static Cache cache = new NoCache();
    private static Network network = new BasicNetwork(new HurlStack());
    private static RequestQueue queue = new RequestQueue(cache, network);

    static {
        queue.start();
    }

    public static void request(String path, int method, JSONObject data, Response.Listener listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(method, url + path, data, listener, errorListener);
        queue.add(request);
    }

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
}
