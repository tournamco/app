package co.tournam.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchModel {
    private String id;
    private String name;
    private Map<String, TeamModel> teams;
    private List<GameModel> games;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MatchTournamentModel tournament;
    private Map<String, Integer> scores;
    private Map<String, Boolean> finished;
    private List<String> keys;

    public MatchModel(String id, String name, LocalDateTime startDate, LocalDateTime endDate,
                      MatchTournamentModel tournament, Map<String, Integer> scores,
                      List<String> keys, List<GameModel> games, Map<String, Boolean> finished,
                      Map<String, TeamModel> teams) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tournament = tournament;
        this.scores = scores;
        this.keys = keys;
        this.games = games;
        this.teams = teams;
        this.finished = finished;
    }

    public int getScore(String key) {
        int score = 0;

        for (GameModel game : games) {
            if (!game.isWinner(key)) continue;

            score++;
        }

        return score;
    }

    public boolean isWinner(TeamModel team) {
        return team == getWinner();
    }

    public TeamModel getWinner() {
        Map<TeamModel, Integer> wins = new HashMap<>();

        for (GameModel game : games) {
            for (String winnerKey : game.getWinners()) {
                TeamModel winner = this.teams.get(winnerKey);

                if (!wins.containsKey(winner)) {
                    wins.put(winner, 1);
                    continue;
                }

                int nWins = wins.get(winner);
                wins.put(winner, nWins++);
            }
        }

        if (wins.size() == 0) {
            return null;
        }

        Map.Entry<TeamModel, Integer> maxEntry = null;

        for (Map.Entry<TeamModel, Integer> entry : wins.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public MatchTournamentModel getTournament() {
        return tournament;
    }

    public void setTournament(MatchTournamentModel tournament) {
        this.tournament = tournament;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Map<String, TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, TeamModel> teams) {
        this.teams = teams;
    }

    public List<GameModel> getGames() {
        return games;
    }

    public void setGames(List<GameModel> games) {
        this.games = games;
    }

    public Map<String, Boolean> getFinished() {
        return finished;
    }

    public static class MatchTournamentModel {
        private String id;
        private String name;
        private int color;

        public MatchTournamentModel(String id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return color;
        }
    }
}
