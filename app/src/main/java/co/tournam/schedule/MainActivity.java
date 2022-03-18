package co.tournam.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import co.tournam.models.FakeData;
import co.tournam.ui.matchlist.SummaryMatchList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        FakeData data = new FakeData();
        addContentView(
                new SummaryMatchList(
                        this.getApplicationContext(),
                        data.tournament.getStages().get(0).getRounds().get(0).getMatches()),
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    }
}