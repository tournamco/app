package co.tournam.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DisputeHandler {
    public static void resolve(String disputeId, String chosenTeamKey, ResolveComplete listener) {
        RequestHandler.request("/dispute/resolve", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();

                json.put("dispute", disputeId);
                json.put("key", chosenTeamKey);

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

    public interface ResolveComplete extends RequestHandler.AbstractCompleted {
        void success();
    }
}
