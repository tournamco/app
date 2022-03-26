package co.tournam.members;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;

public abstract class AbstractMembers extends LinearLayout {

    protected TeamModel team;

    public AbstractMembers(Context context, TeamModel team) {
        super(context);

        this.team = team;

        build(context);
    }

    private void build(Context context) { setOrientation(LinearLayout.VERTICAL); }
}
