package co.tournam.models.stage;

import java.util.List;

import co.tournam.models.RoundModel;

public class StageModel {
    private String type;
    private String name;
    private int numberOfParticipants;
    private List<RoundModel> rounds;
    private List<String> winners;

    public StageModel(String type, String name, int numberOfParticipants, List<RoundModel> rounds,
                      List<String> winners) {
        this.type = type;
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
        this.rounds = rounds;
        this.winners = winners;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public List<RoundModel> getRounds() {
        return rounds;
    }

    public List<String> getWinners() {
        return winners;
    }
}
