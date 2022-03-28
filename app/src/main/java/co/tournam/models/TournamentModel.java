package co.tournam.models;

import java.util.List;

import co.tournam.models.stage.AbstractStageModel;
import co.tournam.models.stage.StageModel;

public class TournamentModel {
    private boolean online;
    private String banner;
    private int currentStage;
    private int gameLength;
    private String id;
    private List<StageModel> stages;
    private List<String> teams;
    private List<DisputeModel> disputes;
    private UserModel organizer;
    private String game;
    private int color;
    private int teamSize;
    private boolean isPublic;
    private String name;
    private String location;

    public TournamentModel(String id, String name, String game, List<String> teams,
                           List<StageModel> stages, UserModel organizer, boolean isPublic,
                           int color, int teamSize, int gameLength, int currentStage, String banner,
                           String location, boolean online) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.teams = teams;
        this.stages = stages;
        this.organizer = organizer;
        this.isPublic = isPublic;
        this.color = color;
        this.teamSize = teamSize;
        this.gameLength = gameLength;
        this.currentStage = currentStage;
        this.banner = banner;
        this.location = location;
        this.online = online;
    }

    public List<StageModel> getStages() {
        return stages;
    }

    public void setStages(List<StageModel> stages) {
        this.stages = stages;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
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

    public String getLocation() { return location; }

    public void setLocation(String location){ this.location = location;}

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
