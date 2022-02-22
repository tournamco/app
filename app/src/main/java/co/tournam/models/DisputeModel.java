package co.tournam.models;

import java.util.List;

public class DisputeModel {
    private MatchModel match;
    private GameModel game;
    private List<ProofModel> proofs;

    public MatchModel getMatch() {
        return match;
    }

    public void setMatch(MatchModel match) {
        this.match = match;
    }

    public GameModel getGame() {
        return game;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public List<ProofModel> getProofs() {
        return proofs;
    }

    public void setProofs(List<ProofModel> proofs) {
        this.proofs = proofs;
    }
}
