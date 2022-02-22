package co.tournam.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MatchModel {
    private List<TeamModel> teams;
    private List<GameModel> games;
    private Date startDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
