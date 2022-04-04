package co.tournam.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.DisputeModel;
import co.tournam.models.MatchModel;

public class DisputeHandler {
    private static DisputeModel fromJSON(JSONObject jsonObject) throws JSONException {
        return new DisputeModel(jsonObject.getString("id"),
                jsonObject.getString("match"),
                jsonObject.getInt("game"),
                jsonObject.getString("tournament"));
    }

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

    public static void list(String tournamentId, ListComplete listener) {
        RequestHandler.request("/dispute/list", Request.Method.GET, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();

                json.put("tournament", tournamentId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                List<DisputeModel> disputes = new ArrayList<>();
                JSONArray disputesData = response.getJSONArray("matches");

                for(int i = 0; i < disputesData.length(); i++) {
                    disputes.add(DisputeHandler.fromJSON(disputesData.getJSONObject(i)));
                }

                listener.success(disputes);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface ListComplete extends RequestHandler.AbstractCompleted {
        void success(List<DisputeModel> disputes);
    }
}
