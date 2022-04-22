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

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.schedule.DiscoveryActivity;
import co.tournam.schedule.DisputesActivity;
import co.tournam.schedule.QRGenActivity;
import co.tournam.schedule.R;

public class TournamentPageHeader extends AbstractHeader {

    private TournamentModel tournament;
    private Bitmap banner;
    private TextView tournamentName;
    public Button disputeButton;
    public Button createButton;
    public Button deleteButton;
    public ImageButton backButton;
    private TextView dateSubText;
    private ImageView background;
    private UserModel me;

    /**
     * Constructor for tournamentPageHeader.
     *
     * @param context    the current context.
     * @param name       the input name.
     * @param tournament the input tournament.
     * @param me         the usermodel of the user.
     */
    public TournamentPageHeader(Context context, String name, TournamentModel tournament, UserModel me) {
        super(context, name);

        this.tournament = tournament;
        this.me = me;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tournament_page_header, this, true);

        //Get the back button
        backButton = findViewById(R.id.tournament_page_header_back_button);

        deleteButton = findViewById(R.id.tournament_page_header_delete_button);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(v -> {
            TournamentHandler.delete(tournament.getId(), () -> {
                Intent intent = new Intent(context, DiscoveryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        });

        createButton = findViewById(R.id.tournament_page_header_create_button);
        createButton.setText("Create");
        createButton.setOnClickListener(v -> {
            TeamHandler.createEmptyTeam(tournament.getId(), false, (teamId, inviteToken) -> {
                Bundle b = new Bundle();
                b.putString("token", inviteToken);
                Intent intent = new Intent(context, QRGenActivity.class);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        });

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

        if (!tournament.getOrganizer().getId().equals(me.getId())) {
            disputeButton.setVisibility(GONE);
            createButton.setVisibility(GONE);
            deleteButton.setVisibility(GONE);
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

    /**
     * sets the background banner
     */
    public void setBanner() {
        //Set the banner as background
        background = findViewById(R.id.tournament_page_header_image_view);
        background.setImageBitmap(banner);

    }

    /**
     * Tournament name setter
     *
     * @param name name of the tournament.
     */
    public void setTournamentName(String name) {
        tournamentName.setText(name);
    }

    /**
     * @param subText the subtext of the UI-element.
     */
    public void setSubText(String subText) {
        dateSubText.setText(subText);
    }
}
