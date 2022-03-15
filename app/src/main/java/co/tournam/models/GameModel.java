package co.tournam.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameModel {
    private Map<TeamModel, Integer> scores;
    private Map<TeamModel, ProofModel> proofs;

    public Map<TeamModel, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<TeamModel, Integer> scores) {
        this.scores = scores;
    }

    public Map<TeamModel, ProofModel> getProofs() {
        return proofs;
    }

    public void setProofs(Map<TeamModel, ProofModel> proofs) {
        this.proofs = proofs;
    }

    public boolean isWinner(TeamModel team) {
        return getWinners().contains(team);
    }

    public List<TeamModel> getWinners() {
        List<TeamModel> winners = new ArrayList<>();
        int topScore = -1;

        if(scores == null) {
            return winners;
        }

        for(TeamModel team : scores.keySet()) {
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
