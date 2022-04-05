package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.ApiErrors;
import co.tournam.api.UserHandler;
import co.tournam.models.UserModel;

public class MyProfileActivity extends AppCompatActivity {

    private LinearLayout userIconLayout;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);
        this.context = this.getApplicationContext();


    }

    public void setTeamIcon() {

        //userIconLayout = (LinearLayout) findViewById(R.id.mypf_userIcon);

        UserHandler.me(new UserHandler.MeCompleted() {
            @Override
            public void success(UserModel me) {

            }

            @Override
            public void failure(ApiErrors error, String message) {

            }
        });

    }


}
