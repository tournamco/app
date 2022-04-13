package co.tournam.ui.header;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import co.tournam.api.DownloadImageWorker;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.schedule.DisputesActivity;
import co.tournam.schedule.R;

public class TournamentPageHeader extends AbstractHeader {

    private TournamentModel tournament;
    private Bitmap banner;
    private TextView tournamentName;
    public Button disputeButton;
    public ImageButton backButton;
    private TextView dateSubText;
    private ImageView background;
    private UserModel me;


    public TournamentPageHeader(Context context, String name, TournamentModel tournament, UserModel me) {
        super(context, name);

        this.tournament = tournament;
        this.me = me;

        build(context);
    }

    private void build(Context context) {
        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tournament_page_header, this, true);

        //Get the back button
        backButton = findViewById(R.id.tournament_page_header_back_button);

        //Get and set the dispute button
        disputeButton = findViewById(R.id.tournament_page_header_dispute_button);
        disputeButton.setText("Disputes");
        disputeButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tournamentid", tournament.getId());
            Intent intent = new Intent(getContext(), DisputesActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

        if(!tournament.getOrganizer().getId().equals(me.getId())) {
            disputeButton.setVisibility(GONE);
        }

        //Get tournament Banner
        new DownloadImageWorker(bitmap -> {
            this.banner = bitmap;
            setBanner();
        }).execute(tournament.getBanner());

        //Get and set the Tournament name header
        tournamentName = findViewById(R.id.tournament_page_header_tournament_name);
        setTournamentName(tournament.getName());

        //Get and set the Sub Text
        dateSubText = findViewById(R.id.tournament_page_header_sub_text);
        setSubText(tournament.getStartDate() + " - " + tournament.getEndDate());

    }

    public void setBanner() {
        //Set the banner as background
        background = findViewById(R.id.tournament_page_header_image_view);
        background.setImageBitmap(banner);

    }

    public void setTournamentName(String name) {
        tournamentName.setText(name);
    }

    public void setSubText(String subText) {
        dateSubText.setText(subText);
    }
}
