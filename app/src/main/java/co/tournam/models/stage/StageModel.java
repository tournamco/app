package co.tournam.models.stage;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import co.tournam.models.RoundModel;

public class StageModel {
    private String type;
    private String name;
    private int numberOfParticipants;
    private List<RoundModel> rounds;
    private List<String> winners;
    private Date minimalDate;
    private Date maximalDate;
    private LocalTime minimalTime;
    private LocalTime maximalTime;

    public StageModel(String type, String name, int numberOfParticipants, List<RoundModel> rounds,
                      List<String> winners, Date minimalDate, Date maximalDate,
                      LocalTime minimalTime, LocalTime maximalTime) {
        this.type = type;
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
        this.rounds = rounds;
        this.winners = winners;
        this.minimalDate = minimalDate;
        this.maximalDate = maximalDate;
        this.minimalTime = minimalTime;
        this.maximalTime = maximalTime;
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

    public Date getMinimalDate() {
        return minimalDate;
    }

    public Date getMaximalDate() {
        return maximalDate;
    }

    public LocalTime getMinimalTime() {
        return minimalTime;
    }

    public LocalTime getMaximalTime() {
        return maximalTime;
    }
}
