package co.tournam.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameModel {
    private Map<String, Integer> scores;
    private Map<String, String> proofs;

    public GameModel(Map<String, Integer> scores, Map<String, String> proofs) {
        this.scores = scores;
        this.proofs = proofs;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, String> getProofs() {
        return proofs;
    }

    public void setProofs(Map<String, String> proofs) {
        this.proofs = proofs;
    }

    public boolean isWinner(String key) {
        return getWinners().contains(key);
    }

    public List<String> getWinners() {
        List<String> winners = new ArrayList<>();
        int topScore = -1;

        if(scores == null) {
            return winners;
        }

        for(String team : scores.keySet()) {
            int score = scores.get(team);

            if(score == topScore) {
                winners.add(team);
            }
            else if(score > topScore) {
                winners.clear();
                winners.add(team);
            }
        }

        return winners;
    }
}
