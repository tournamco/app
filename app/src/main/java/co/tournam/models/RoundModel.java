package co.tournam.models;

import java.util.List;

import co.tournam.models.stage.AbstractStageModel;

public class RoundModel {
    private List<String> matches;
    private String name;

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
