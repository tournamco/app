package co.tournam.api;

import android.util.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.tournam.models.ProofModel;

public class ProofHandler {
    public static ProofModel fromJSON(JSONObject json) throws JSONException {
        JSONObject scoresData = json.getJSONObject("scores");
        Map<String, Integer> scores = new ArrayMap<>();

        for(int i = 0; i < scoresData.names().length(); i++) {
            scores.put(scoresData.names().getString(i), scoresData.getInt(scoresData.names().getString(i)));
        }

        JSONArray imagesData = json.getJSONArray("images");
        List<String> images = new ArrayList<>();

        for(int i = 0; i < imagesData.length(); i++) {
            images.add(imagesData.getString(i));
        }

        return new ProofModel(
                json.getString("id"),
                json.getString("team"),
                scores, images
        );
    }

    public static void create(String matchId, int gameIndex, CreateComplete listener) {
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

    public interface CreateComplete extends RequestHandler.AbstractCompleted {
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

    public static void removeImage(String proofId, String imageId, RemoveImageComplete listener) {
        RequestHandler.request("/proof/image/remove", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("id", proofId);
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

    public interface RemoveImageComplete extends RequestHandler.AbstractCompleted {
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

    public static void info(String proofId, InfoComplete listener) {
        RequestHandler.request("/proof/info", Request.Method.POST, new RequestHandler.RequestSetup() {
            @Override
            public JSONObject body() throws JSONException {
                JSONObject json = new JSONObject();
                json.put("id", proofId);

                return json;
            }

            @Override
            public void success(JSONObject response) throws JSONException {
                listener.success(ProofHandler.fromJSON(response));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                listener.failure(error, message);
            }
        });
    }

    public interface InfoComplete extends RequestHandler.AbstractCompleted {
        void success(ProofModel proof);
    }
}
