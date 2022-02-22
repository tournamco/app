package co.tournam.models;

import java.util.Map;

public class GameModel {
    private int[] scores;
    private Map<TeamModel, ProofModel> proofs;

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public Map<TeamModel, ProofModel> getProofs() {
        return proofs;
    }

    public void setProofs(Map<TeamModel, ProofModel> proofs) {
        this.proofs = proofs;
    }
}
