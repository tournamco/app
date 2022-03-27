package co.tournam.models.stage;

import java.util.List;

import co.tournam.models.RoundModel;

public class StageModel {
    private String type;
    private String name;
    private int numberOfParticipants;
    private List<RoundModel> rounds;
    private List<String> winners;
}
