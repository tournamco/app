package co.tournam.schedule;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import co.tournam.members.Members;
import co.tournam.schedule.schedule.Schedule;
import co.tournam.ui.big_header;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   addContentView(
                new big_header(
                        this.getApplicationContext()
                ),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        addContentView(
                new TournamentSummaryListItem(
                      //  this.getApplicationContext(),
                        //data.tournament),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav_view);
        //getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new Search()).commit();

        bottomNav.setSelectedItemId(R.id.navHostFragment);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_tournament:
                        //selectedFragment = new Tournament();
                        break;
                    case R.id.navigation_search:
                      //  selectedFragment = new Search();
                        break;
                    case R.id.navigation_schedule:
                        selectedFragment = new Schedule();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, selectedFragment).commit();
                return true;
            }
        });*/
    }


}
