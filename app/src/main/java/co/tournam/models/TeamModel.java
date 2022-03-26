package co.tournam.models;

import android.graphics.Bitmap;

import java.util.List;

public class TeamModel {
    private List<UserModel> members;
    private UserModel leader;
    private String name;
    private Bitmap image;

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image){this.image = image;}
}
