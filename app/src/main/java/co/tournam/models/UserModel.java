package co.tournam.models;

public class UserModel {
    private String username;
    private String gamerTag;
    private String email;
    private int iconID;
    private String tournamentWinRatio;
    private String dateJoined;
    private String winRatio;
    private int matchWins;
    private int tournamentWins;
    private String image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIcon() { return iconID; }

    public void setIcon(int iconID) { this.iconID = iconID; }
    public void setDateJoined(){ this.dateJoined = dateJoined; }

    public String getDateJoined(){ return dateJoined; }

    public String getTournamentWinRatio(){ return tournamentWinRatio;}

    public void setTournamentWinRatio(String tournamentWinRatio) {this.tournamentWinRatio = tournamentWinRatio;}

    public String getWinRatio(){ return winRatio;}

    public void setWinRatio(String winRatio) {this.winRatio = winRatio;}

    public int getMatchWins(){ return matchWins;}

    public void setWinRatio(int matchWins) {this.matchWins = matchWins;}

    public int getTournamentWins(){ return tournamentWins;}

    public void setTournamentWins(int tournamentWins) {this.tournamentWins = tournamentWins;}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
