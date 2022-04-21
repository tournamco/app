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
    private UserList.UserListActionListener listener;
    private Context context;

    /**
     * The Constructor for the UserList.
     *
     * @param context    the current context
     * @param users      the list of usermodels
     * @param buttonText the text on the button
     * @param listener   the listener used by the class.
     */
    public UserList(Context context, List<UserModel> users, String buttonText, UserList.UserListActionListener listener) {
        super(context);

        this.listener = listener;
        this.users = users;
        this.buttonText = buttonText;
        this.context = context;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {
        for (UserModel user : users) {
            this.addUser(user);
        }
    }

    /**
     * A method for adding a user
     *
     * @param user the usermodel that is being added.
     */
    public void addUser(UserModel user) {
        UserListRow row = new UserListRow(context, user, buttonText, listener);
        this.addView(row);
    }

    /**
     * the interface being used by the class.
     */
    public interface UserListActionListener {
        void onUserSelected(UserModel team);
    }
}
