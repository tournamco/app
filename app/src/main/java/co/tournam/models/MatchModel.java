package co.tournam.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.tournam.models.stage.AbstractStageModel;

public class MatchModel {
    private List<TeamModel> teams;
    private List<GameModel> games;
    private LocalDateTime startDate;
    private TournamentModel tournament;
    private RoundModel round;

    public List<TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamModel> teams) {
        this.teams = teams;
    }

    public List<GameModel> getGames() {
        return games;
    }

    public void setGames(List<GameModel> games) {
        this.games = games;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getScore(TeamModel team) {
        int score = 0;

        for(GameModel game : games) {
            if(!game.isWinner(team)) continue;

            score++;
        }

        return score;
    }

    public boolean isWinner(TeamModel team) {
        return team == getWinner();
    }

    public TeamModel getWinner() {
        Map<TeamModel, Integer> wins = new HashMap<>();

        for(GameModel game : games) {
            for(TeamModel winner : game.getWinners()) {
                System.out.println("help " + winner.getName());
                if(!wins.containsKey(winner)) {
                    wins.put(winner, 1);
                    continue;
                }

                int nWins = wins.get(winner);
                wins.put(winner, nWins++);
            }
        }

        if(wins.size() == 0) {
            return null;
        }

        Map.Entry<TeamModel, Integer> maxEntry = null;

        for (Map.Entry<TeamModel, Integer> entry : wins.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public void setTournament(TournamentModel tournament) {
        this.tournament = tournament;
    }

    public TournamentModel getTournament() {
        return tournament;
    }

    public RoundModel getRound() {
        return round;
    }

    public void setRound(RoundModel round) {
        this.round = round;
    }

    public String getFullName() {
        AbstractStageModel stage = round.getStage();

        return tournament.getName() + ", " + stage.getName() + " - " + round.getName();
    }
}
