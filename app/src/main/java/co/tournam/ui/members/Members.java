package co.tournam.ui.members;

import android.content.Context;
import android.widget.LinearLayout;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class Members extends AbstractMembers {

    TeamModel team;
    String buttonText;

    public Members(Context context, TeamModel team, String buttonText) {
        super(context, team);

        this.team = team;
        this.buttonText = buttonText;

        build(context);
    }

    /**
     * Allows the modification of the Linearlayout before building its components
     *
     * @param context Android context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        buildContents(context);
    }

    /**
     * Builds the components of the layout
     *
     * @param context Android Context
     */
    private void buildContents(Context context) {
        for (UserModel user : team.getMembers()) {
            MembersListItem item = new MembersListItem(context, user, buttonText);

            this.addView(item);
        }
    }
}
