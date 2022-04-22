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
    /**
     * Parse the JSON response from the API and return proof model
     * @param json The JSONObject to parse
     * @return The proof model
     * @throws JSONException if the JSONObject is invalid
     */
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

    /**
     * API call to create a new proof
     * @param matchId The match id
     * @param gameIndex The game index
     * @param listener The listener to call when the request is complete
     */
    public static void create(String matchId, int gameIndex, CreateComplete listener) {
        RequestHandler.request("/proof/create", Request.Method.POST, () -> {
                JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("game", gameIndex);

                return json;
            }, response -> {
                String proofId = response.getString("id");

                listener.success(proofId);
            });
    }

    /**
     * Interface for the create API call
     */
    public interface CreateComplete {
        void success(String proofId);
    }

    /**
     * API call to add an image to a proof
     * @param matchId The match id
     * @param proofId The proof id
     * @param imageId The image id
     * @param listener The listener to call when the request is complete
     */
    public static void addImage(String matchId, String proofId, String imageId, AddImageComplete listener) {
        RequestHandler.request("/proof/image/add", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("proof", proofId);
                json.put("image", imageId);

                return json;
            }, response -> listener.success());
    }

    /**
     * Interface for the add image API call
     */
    public interface AddImageComplete {
        void success();
    }

    /**
     * API call to set the scores of team for a proof
     * @param matchId The match id
     * @param proofId The proof id
     * @param scores The scores to set
     * @param listener The listener to call when the request is complete
     */
    public static void setScores(String matchId, String proofId, Map<String, Integer> scores, SetScoresComplete listener) {
        RequestHandler.request("/proof/scores/set", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("match", matchId);
                json.put("proof", proofId);
                JSONObject scoresObj = new JSONObject();
                for(String key : scores.keySet()) {
                    scoresObj.put(key, scores.get(key));
                }
                json.put("scores", scoresObj);

                return json;
            }, response -> listener.success());
    }

    /**
     * Interface for the set scores API call
     */
    public interface SetScoresComplete {
        void success();
    }

    /**
     * API call to get the proof for a match
     * @param proofId The proof id
     * @param listener The listener to call when the request is complete
     */
    public static void info(String proofId, InfoComplete listener) {
        RequestHandler.request("/proof/info", Request.Method.POST, () -> {
            JSONObject json = new JSONObject();
                json.put("id", proofId);

                return json;
            }, response -> listener.success(ProofHandler.fromJSON(response.getJSONObject("proof"))));
    }

    /**
     * Interface for the info API call
     */
    public interface InfoComplete {
        void success(ProofModel proof);
    }
}
