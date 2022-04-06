package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.UserHandler;



public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this.getApplicationContext();

        if(!UserHandler.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            return;
        } else {
            startActivity(new Intent(getApplicationContext(), ScheduleActivity.class));
        }
    }
}