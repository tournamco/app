package co.tournam.models;

import java.util.List;

public class TeamModel {
    private List<UserModel> members;
    private UserModel leader;

    public List<UserModel> getMembers() {
        return members;
    }

    public void setMembers(List<UserModel> members) {
        this.members = members;
    }

    public UserModel getLeader() {
        return leader;
    }

    public void setLeader(UserModel leader) {
        this.leader = leader;
    }
}
