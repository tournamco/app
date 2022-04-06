package co.tournam.models;

import java.util.List;

public class TeamModel {
    private String id;
    private List<UserModel> members;
    private UserModel leader;
    private String name;
    private String icon;
    private String tournamentId;
    private boolean isPublic;

    public TeamModel(String id, String name, UserModel leader, List<UserModel> members,
                     boolean isPublic, String icon, String tournamentId) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.members = members;
        this.isPublic = isPublic;
        this.icon = icon;
        this.tournamentId = tournamentId;
    }
    public String getID() { return id; }

    public List<UserModel> getMembers() {
        return members;
    }

    public void setMembers(List<UserModel> members) {
        this.members = members;
    }

    public int getCurrentMemberAmount() { return (int) this.members.stream().count(); }

    public UserModel getLeader() {
        return leader;
    }

    public void setLeader(UserModel leader) {
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() { return icon;}

    public String getTournamentId() { return tournamentId; }
}
