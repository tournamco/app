package co.tournam.models;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class TournamentModel {
    private boolean online;
    private String banner;
    private int currentStage;
    private int gameLength;
    private String id;
    private List<StageModel> stages;
    private List<String> teams;
    private UserModel organizer;
    private String game;
    private int color;
    private int teamSize;
    private boolean isPublic;
    private String name;
    private TournamentLocation location;

    //Constructor of a Tournament Model
    public TournamentModel(String id, String name, String game, List<String> teams,
                           List<StageModel> stages, UserModel organizer, boolean isPublic,
                           int color, int teamSize, int gameLength, int currentStage, String banner,
                           TournamentLocation location, boolean online) {
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

    public int getCurrentAmountOfTeams() {
        return (int) teams.stream().count();
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

    public TournamentLocation getLocation() {
        return location;
    }

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

    public String getStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM");

        return formatter.format(stages.get(0).getMinimalDate());
    }

    public String getEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM");

        return formatter.format(stages.get(stages.size() - 1).getMaximalDate());
    }

    //Static class for determining the tournament location
    public static class TournamentLocation {
        private float longitude;
        private float latitude;

        public TournamentLocation(float longitude, float latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public float getLatitude() {
            return latitude;
        }
    }

    //Static class for generating stages
    public static class CreateStageModel {
        private StageTypes type;
        private String name;
        private int numberOfParticipants;
        private String minimalDate;
        private String maximalDate;
        private String minimalTime;
        private String maximalTime;
        private Map<String, Integer> options;

        public CreateStageModel(StageTypes type, String name, int numberOfParticipants,
                                String minimalDate, String maximalDate, String minimalTime,
                                String maximalTime, Map<String, Integer> options) {
            this.type = type;
            this.name = name;
            this.numberOfParticipants = numberOfParticipants;
            this.minimalDate = minimalDate;
            this.maximalDate = maximalDate;
            this.minimalTime = minimalTime;
            this.maximalTime = maximalTime;
            this.options = options;
        }

        public StageTypes getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public int getNumberOfParticipants() {
            return numberOfParticipants;
        }

        public String getMinimalDate() {
            return minimalDate;
        }

        public String getMaximalDate() {
            return maximalDate;
        }

        public String getMinimalTime() {
            return minimalTime;
        }

        public String getMaximalTime() {
            return maximalTime;
        }

        public Map<String, Integer> getOptions() {
            return options;
        }
    }

    public enum StageTypes {
        SWISS_ELIMINATION("swiss"),
        SINGLE_ELIMINATION("single"),
        DOUBLE_ELIMINATION("double"),
        POOLS("pools");

        private final String type;

        StageTypes(String type) {
            this.type = type;
        }

        public String getValue() {
            return this.type;
        }
    }
}
