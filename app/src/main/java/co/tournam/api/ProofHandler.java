package co.tournam.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ProofHandler {
    public static void resolve(String matchId, int gameIndex, ResolveComplete listener) {
        RequestHandler.request("/proof/create", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("game", gameIndex);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                String proofId = response.getString("id");

                listener.success(proofId);
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface ResolveComplete extends RequestHandler.AbstractCompleted {
        void success(String proofId);
    }

    public static void addImage(String matchId, String proofId, String imageId, AddImageComplete listener) {
        RequestHandler.request("/proof/image/add", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("proof", proofId);
                json.put("image", imageId);

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

    public interface AddImageComplete extends RequestHandler.AbstractCompleted {
        void success();
    }

    public static void setScores(String matchId, String proofId, Map<String, Integer> scores, SetScoresComplete listener) {
        RequestHandler.request("/proof/scores/set", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("proof", proofId);
                JSONObject scoresObj = new JSONObject();
                for(String key : scores.keySet()) {
                    scoresObj.put(key, scores.get(key));
                }
                json.put("scores", scoresObj);

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

    public interface SetScoresComplete extends RequestHandler.AbstractCompleted {
        void success();
    }
}
