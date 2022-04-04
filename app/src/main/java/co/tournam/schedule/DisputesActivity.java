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
import co.tournam.ui.dispute.Dispute;
import co.tournam.ui.gameproof.GameProof;

public class DisputesActivity extends AppCompatActivity {

    Context context;
    private LinearLayout disputesLayout;
    private FakeData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_disputes);
        data = new FakeData();
        context = this.getApplicationContext();

        setDisputes();
    }

    private void setDisputes() {
        disputesLayout = (LinearLayout) findViewById(R.id.proof);


    }

}
