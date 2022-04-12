package co.tournam.ui.list;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import androidx.appcompat.widget.LinearLayoutCompat;
import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class UserList extends LinearLayout {
    private List<UserModel> users;
    private String buttonText;
    private TeamList.TeamListActionListener listener;
    private Context context;

    public UserList(Context context, List<UserModel> users, String buttonText, TeamList.TeamListActionListener listener) {
        super(context);

        this.listener = listener;
        this.users = users;
        this.buttonText = buttonText;
        this.context = context;

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    public void buildContents(Context context) {
        for (UserModel user : users) {
            this.addUser(user);
        }
    }

    public void addUser(UserModel user) {
        UserListRow row = new UserListRow(context, user, buttonText, listener);
        this.addView(row);
    }

    public interface UserListActionListener {
        void onUserSelected(UserModel team);
    }
}
