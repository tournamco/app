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
    /**
     * Converts a JSONObject to a DisputeModel
     * @param jsonObject The JSONObject to convert
     * @return The DisputeModel
     * @throws JSONException
     */
    private static DisputeModel fromJSON(JSONObject jsonObject) throws JSONException {
        return new DisputeModel(jsonObject.getString("id"),
                jsonObject.getString("match"),
                jsonObject.getInt("game"),
                jsonObject.getString("tournament"));
    }

    /**
     * API call to resolve a dispute
     * @param disputeId The dispute id
     * @param chosenTeamKey The team key of the team that won the match
     * @param listener The listener to call when the request is complete
     */
    public static void resolve(String disputeId, String chosenTeamKey, ResolveComplete listener) {
        RequestHandler.request("/dispute/resolve", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("dispute", disputeId);
                json.put("key", chosenTeamKey);

                return json;
            }, response -> listener.success());
    }

    /**
     * Interface for the resolve request
     */
    public interface ResolveComplete {
        void success();
    }

    /**
     * API call to list all disputes
     * @param tournamentId The tournament id
     * @param listener The listener to call when the request is complete
     */
    public static void list(String tournamentId, ListComplete listener) {
        RequestHandler.request("/dispute/list", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("tournament", tournamentId);

                return json;
            }, response -> {
                List<DisputeModel> disputes = new ArrayList<>();
                JSONArray disputesData = response.getJSONArray("disputes");

                for(int i = 0; i < disputesData.length(); i++) {
                    disputes.add(DisputeHandler.fromJSON(disputesData.getJSONObject(i)));
                }

                listener.success(disputes);
            });
    }

    /**
     * Interface for the list request
     */
    public interface ListComplete {
        void success(List<DisputeModel> disputes);
    }
}
