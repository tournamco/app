package co.tournam.models;

import java.util.List;

import co.tournam.models.stage.AbstractStageModel;

public class RoundModel {
    private List<MatchModel> matches;
    private AbstractStageModel stage;
    private String name;

    public List<MatchModel> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchModel> matches) {
        this.matches = matches;
    }

    public AbstractStageModel getStage() {
        return stage;
    }

    public void setStage(AbstractStageModel stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
