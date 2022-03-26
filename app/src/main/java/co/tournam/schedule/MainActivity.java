package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
//import com.google.firebase.auth.FirebaseAuth;

import co.tournam.api.UserHandler;
import co.tournam.models.FakeData;
import co.tournam.schedule.schedule.Schedule;
import co.tournam.schedule.schedule.Search;
import co.tournam.schedule.schedule.Tournament;


public class MainActivity extends AppCompatActivity {

    RecyclerView recycleView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FakeData data = new FakeData();
        this.context = this.getApplicationContext();

        if(!UserHandler.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            return;
        }

//        LinearLayout layout1 = (LinearLayout) findViewById(R.id.list1);
//        layout1.addView( new Members(
//                        this.getApplicationContext(),
//                        data.tournament.getTeams().get(0)));
//        layout1.addView( new TournamentSummaryListItem(
//                this.getApplicationContext(),
//                data.tournament));
        /*addContentView(
                new Members(
                        this.getApplicationContext(),
                        data.tournament.getTeams().get(0)),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        addContentView(
                new TournamentSummaryListItem(
                        this.getApplicationContext(),
                        data.tournament),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));*/

//        FakeData data = new FakeData();
//        addContentView(
//                new SummaryMatchList(
//                        this.getApplicationContext(),
//                        data.tournament.getStages().get(0).getRounds().get(0).getMatches()),
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));


        BottomNavigationView bottomNav = findViewById(R.id.bottomNav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new Search()).commit();

        bottomNav.setSelectedItemId(R.id.navHostFragment);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
            }
        });
        Intent switchActivityIntent = new Intent(this, ManageTeamActivity.class);
        startActivity(switchActivityIntent);
//    }

//        public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();

//        finish();
    }


}