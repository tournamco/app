package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import co.tournam.models.FakeData;
import co.tournam.team_members.TeamMembers;
import co.tournam.ui.matchlist.SummaryMatchList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           FakeData data = new FakeData();
           addContentView(
                   new TeamMembers(
                           this.getApplicationContext(),
                           data.tournament.getTeams()),
                           new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
          /* addContentView(
                  new SummaryMatchList(
                           this.getApplicationContext(),
                           data.tournament.getStages().get(0).getRounds().get(0).getMatches()),
                   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));*/
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


}