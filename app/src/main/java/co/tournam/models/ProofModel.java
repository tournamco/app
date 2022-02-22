package co.tournam.models;

import android.media.Image;

import java.util.List;

public class ProofModel {
    private List<Integer> scores;
    private List<Image> proof;

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public List<Image> getProof() {
        return proof;
    }

    public void setProof(List<Image> proof) {
        this.proof = proof;
    }
}
