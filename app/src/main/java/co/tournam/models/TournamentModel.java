package co.tournam.models;

import android.graphics.Color;

import java.util.List;

import co.tournam.models.stage.AbstractStageModel;

public class TournamentModel {
    private List<AbstractStageModel> stages;
    private List<TeamModel> teams;
    private List<DisputeModel> disputes;
    private UserModel organizer;
    private String game;
    private int color;
    private int teamSize;
    private boolean isPublic;
    private String name;
    private int imageID;
    private int teamLimit;

    public List<AbstractStageModel> getStages() {
        return stages;
    }

    public void setStages(List<AbstractStageModel> stages) {
        this.stages = stages;
    }

    public List<TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamModel> teams) {
        this.teams = teams;
    }

    public int getCurrentAmountOfTeams() { return (int) teams.stream().count(); }

    public List<DisputeModel> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<DisputeModel> disputes) {
        this.disputes = disputes;
    }

    public UserModel getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserModel organizer) {
        this.organizer = organizer;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageID(int imageID) { this.imageID = imageID; }

    public int getImageID() { return imageID; }

    public void setTournamentIcon(int imageID) { this.imageID = imageID; }

    public int getTournamentIcon() { return imageID; }

    public int getTournamentLimit() { return teamLimit; }
}
