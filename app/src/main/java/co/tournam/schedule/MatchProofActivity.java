package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.FakeData;
import co.tournam.ui.gameproof.GameProof;

public class MatchProofActivity extends AppCompatActivity {

    Context context;
    private LinearLayout matchProofLayout;
    private FakeData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_proof);
        //data = new FakeData();
        context = this.getApplicationContext();

        setDisputes();
    }

    private void setDisputes() {
        matchProofLayout = (LinearLayout) findViewById(R.id.proof);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.search_playstore);
        //Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.imagelist_add_plus);
        //Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.change2);
        //Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.change2);
        //Bitmap bm5 = BitmapFactory.decodeResource(getResources(), R.drawable.change2);
        //List<Bitmap> bitList = new ArrayList<Bitmap>();
        //bitList.add(bm);
        //bitList.add(bm2);
        //bitList.add(bm3);
        //bitList.add(bm4);
        //bitList.add(bm5);
        matchProofLayout.addView(new GameProof(context, , ));
        matchProofLayout.addView(new GameProof(context, , ));
    }


}
