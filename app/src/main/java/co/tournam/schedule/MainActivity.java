package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import co.tournam.api.UserHandler;
import co.tournam.schedule.schedule.Schedule;
import co.tournam.schedule.schedule.Search;
import co.tournam.schedule.schedule.Tournament;



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
        }


        BottomNavigationView bottomNav = findViewById(R.id.bottomNav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new Search()).commit();

        bottomNav.setSelectedItemId(R.id.navHostFragment);
        bottomNav.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    selectedFragment = new Tournament();
                    break;
                case R.id.navigation_search:
                    selectedFragment = new Search();
                    break;
                case R.id.navigation_schedule:
                    selectedFragment = new Schedule();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, selectedFragment).commit();
            return true;
        });

    }
}