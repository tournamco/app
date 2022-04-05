package co.tournam.models;

import java.util.List;
import java.util.Map;

public class ProofModel {
    private String id;
    private String teamId;
    private Map<String, Integer> scores;
    private List<String> images;

    public ProofModel(String id, String teamId, Map<String, Integer> scores, List<String> images) {
        this.id = id;
        this.teamId = teamId;
        this.scores = scores;
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }
}
