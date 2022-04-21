package co.tournam.ui.members;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class MembersListItem extends LinearLayout {
    private UserModel user;

    private ImageView logo;
    private TextView playerName;
    private String buttonText;
    private Button actionButton;

    public MembersListItem(Context context, UserModel user, String buttonText) {
        super(context);

        this.buttonText = buttonText;
        this.user = user;

        build(context);
    }

    /**
     * Allows the modification of the Linearlayout before building its components
     *
     * @param context Android context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        buildContents(context);
    }

    /**
     * Builds the components of the layout
     *
     * @param context Android Context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_team, this, true);

        logo = (ImageView) findViewById(R.id.imaget);
        playerName = (TextView) findViewById(R.id.namet);
        actionButton = (Button) findViewById(R.id.button2);
        setButtonText(buttonText);

        setName(user.getGamerTag());
    }

    private void setName(String name) {
        playerName.setText(name);
    }

    private void setIcon(int imageID) {
        logo.setImageResource(imageID);
    }

    private void setButtonText(String text) {
        actionButton.setText(text);
    }
}
