package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.UserHandler;


public class MainActivity extends AppCompatActivity {

    Context context;

    //On launch the app check if the user is logged in, and redirects them accordingly
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("LocationInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this.getApplicationContext();

        if (!UserHandler.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            return;
        } else {
            startActivity(new Intent(getApplicationContext(), ScheduleActivity.class));
        }
    }
}