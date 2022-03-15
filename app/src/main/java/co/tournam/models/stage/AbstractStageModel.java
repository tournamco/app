package co.tournam.models.stage;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import co.tournam.models.RoundModel;
import co.tournam.models.TournamentModel;

public abstract class AbstractStageModel {
    private Date minimalDate;
    private Date maximalDate;
    private Duration minimalTime;
    private Duration maximalTime;
    private List<RoundModel> rounds;
    private int numberOfParticipants;
    private TournamentModel tournament;
    private String name;

    public Date getMinimalDate() {
        return minimalDate;
    }

    public void setMinimalDate(Date minimalDate) {
        this.minimalDate = minimalDate;
    }

    public Date getMaximalDate() {
        return maximalDate;
    }

    public void setMaximalDate(Date maximalDate) {
        this.maximalDate = maximalDate;
    }

    public Duration getMinimalTime() {
        return minimalTime;
    }

    public void setMinimalTime(Duration minimalTime) {
        this.minimalTime = minimalTime;
    }

    public Duration getMaximalTime() {
        return maximalTime;
    }

    public void setMaximalTime(Duration maximalTime) {
        this.maximalTime = maximalTime;
    }

    public List<RoundModel> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundModel> rounds) {
        this.rounds = rounds;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public TournamentModel getTournament() {
        return tournament;
    }

    public void setTournament(TournamentModel tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
