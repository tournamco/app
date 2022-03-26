package co.tournam.schedule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.Text;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.headerDiscovery;
import co.tournam.ui.tournament_summary.TournamentSummaryListJoin;

public class DiscoveryActivity extends LinearLayout {

    Resources res = getResources();
    Drawable qr = ResourcesCompat.getDrawable(res, R.drawable.qr_icon, null);
    Drawable nfc = ResourcesCompat.getDrawable(res, R.mipmap.ic_nfc, null);

    public DiscoveryActivity(Context context) {

        super(context);

        build(context);
    }

    public void build(Context context) {

        setOrientation(VERTICAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,3));


        rootLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
               LayoutParams.MATCH_PARENT,
               LayoutParams.WRAP_CONTENT,
               16
       );

        // TODO: GET MATCHLIST FROM SERVER
        List<TournamentModel> fake = new ArrayList<TournamentModel>();


        DefaultButton button = new DefaultButton(this.getContext(), "Create");

        headerDiscovery header = new headerDiscovery(this.getContext(), "Discovery");

        TournamentSummaryListJoin list = new TournamentSummaryListJoin(this.getContext(), fake);

        list.setLayoutParams(param);

        Slider slider = new Slider(this.getContext(), true);


        DefaultButtonIMG qrJoin = new DefaultButtonIMG(this.getContext(), "Join using QR code", qr);

        qrJoin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,6));

        DefaultButtonIMG nfcJoin = new DefaultButtonIMG(this.getContext(), "Join using NFC", nfc);

        qrJoin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,6));

        Text text = new Text(this.getContext(), "Tournaments");

        text.setLayoutParams(param);
        this.addView(header);
        this.addView(qrJoin);
        this.addView(nfcJoin);
        this.addView(slider,3);

        this.addView(text,4);

        this.addView(list);


    }

}
