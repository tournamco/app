package co.tournam.models;

public class UserModel {
    private String username;
    private String gamerTag;
    private String email;
    private int iconID;

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
}
