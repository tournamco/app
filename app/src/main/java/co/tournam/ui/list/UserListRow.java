package co.tournam.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import co.tournam.api.DownloadImageWorker;
import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class UserListRow extends LinearLayout {
    private UserModel user;
    private String buttonText;
    private ImageView userIcon;
    private TextView userName;
    public Button actionButton;
    private UserList.UserListActionListener listener;

    /**
     * The constructor for UserListRow
     *
     * @param context    the current context.
     * @param user       the user model.
     * @param buttonText the button text
     * @param listener   the listener used by the class.
     */
    public UserListRow(Context context, UserModel user, String buttonText, UserList.UserListActionListener listener) {
        super(context);

        this.user = user;
        this.buttonText = buttonText;
        this.listener = listener;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_user, this, true);

        userIcon = (ImageView) findViewById(R.id.imaget);
        userName = (TextView) findViewById(R.id.namet);
        actionButton = (Button) findViewById(R.id.button2);
        if (buttonText != null) {
            actionButton.setOnClickListener(view -> {
                listener.onUserSelected(user);
            });
            setButtonText(buttonText);
        } else {
            actionButton.setVisibility(GONE);
        }

        new DownloadImageWorker(icon -> userIcon.setImageBitmap(icon)).execute(user.getIcon());
        userName.setText(user.getGamerTag());
    }

    /**
     * setter for the text on the button
     *
     * @param text the input text.
     */
    public void setButtonText(String text) {
        actionButton.setText(text);
    }
}
