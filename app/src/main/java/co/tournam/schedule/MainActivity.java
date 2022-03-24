package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import co.tournam.models.FakeData;
import co.tournam.ui.matchlist.SummaryMatchList;



public class MainActivity extends AppCompatActivity{

    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FakeData data = new FakeData();
        addContentView(
                new SummaryMatchList(
                        this.getApplicationContext(),
                        data.tournament.getStages().get(0).getRounds().get(0).getMatches()),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));


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

    }



        public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


}