package co.tournam.members;

import android.content.Context;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class Members extends AbstractMembers{

    public Members(Context context, TeamModel team) {
        super(context, team);

        build(context);
    }

    private void build(Context context) { buildContents(context); }

    private void buildContents(Context context) {
        for (UserModel user : team.getMembers()) {
            MembersListItem item = new MembersListItem(context, user);

            this.addView(item);
        }
    }
}