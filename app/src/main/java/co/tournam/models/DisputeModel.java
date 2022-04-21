package co.tournam.models;

public class DisputeModel {
    private String id;
    private String matchId;
    private int gameIndex;
    private String tournamentId;

    //Constructor of a Dispute Model
    public DisputeModel(String id, String matchId, int gameIndex, String tournamentId) {
        this.id = id;
        this.matchId = matchId;
        this.gameIndex = gameIndex;
        this.tournamentId = tournamentId;
    }

    public String getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public String getTournamentId() {
        return tournamentId;
    }
}
