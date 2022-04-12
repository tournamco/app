package co.tournam.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import co.tournam.api.DownloadImageWorker;
import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;
import co.tournam.schedule.OurTeamActivity;
import co.tournam.schedule.R;

public class UserListRow extends LinearLayout {
    private UserModel user;
    private String buttonText;
    private ImageView userIcon;
    private TextView userName;
    public Button actionButton;
    private UserList.UserListActionListener listener;

    public UserListRow(Context context, UserModel user, String buttonText, UserList.UserListActionListener listener) {
        super(context);

        this.user = user;
        this.buttonText = buttonText;
        this.listener = listener;

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row_user, this, true);

        userIcon = (ImageView) findViewById(R.id.imaget);
        userIcon.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("teamid", user.getId());
            Intent intent = new Intent(context, OurTeamActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        userName = (TextView) findViewById(R.id.namet);
        actionButton = (Button) findViewById(R.id.button2);
        if(buttonText != null) {
            actionButton.setOnClickListener(view -> {
                listener.onUserSelected(user);
            });
            setButtonText(buttonText);
        }
        else {
            actionButton.setVisibility(GONE);
        }

        new DownloadImageWorker(icon -> userIcon.setImageBitmap(icon)).execute(user.getIcon());
        userName.setText(user.getUsername());
    }

    public void setButtonText(String text) {
        actionButton.setText(text);
    }
}
