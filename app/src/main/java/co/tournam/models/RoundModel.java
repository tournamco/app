package co.tournam.models;

import java.util.List;

import co.tournam.models.stage.AbstractStageModel;

public class RoundModel {
    private List<String> matches;
    private String name;

    public RoundModel(String name, List<String> matches) {
        this.name = name;
        this.matches = matches;
    }

    public List<String> getMatches() {
        return matches;
    }

    public String getName() {
        return name;
    }
}
