package co.tournam.models;

import java.util.List;

public class RoundModel {
    private List<MatchModel> matches;

    public List<MatchModel> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchModel> matches) {
        this.matches = matches;
    }
}
