package co.tournam.models;

public class UserModel {
    private String id;
    private String username;
    private String gamerTag;
    private String email;
    private String icon;

    public UserModel(String id, String username, String gamerTag, String email, String icon) {
        this.id = id;
        this.username = username;
        this.gamerTag = gamerTag;
        this.email = email;
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public String getEmail() {
        return email;
    }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }
}
