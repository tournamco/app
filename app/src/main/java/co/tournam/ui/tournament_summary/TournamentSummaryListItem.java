package co.tournam.ui.tournament_summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.models.TournamentModel;
import co.tournam.schedule.JoinTournamentActivity;
import co.tournam.schedule.R;
import co.tournam.schedule.TournamentPageActivity;

public class TournamentSummaryListItem extends LinearLayout {

    private TournamentModel tournament;
    private ImageView tournamentBanner;
    private TextView gameName;
    private TextView name;
    private TextView dateText;

    /**
     * The constructor for TournamentSummaryListItem.
     *
     * @param context    the current context.
     * @param tournament the tournamentModel.
     */
    public TournamentSummaryListItem(Context context, TournamentModel tournament) {
        super(context);

        this.tournament = tournament;

        build(context);

    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

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
        inflater.inflate(R.layout.tournament_summary, this, true);

        tournamentBanner = (ImageView) findViewById(R.id.banner);
        setTournamentBanner(tournament);
        tournamentBanner.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tournamentid", tournament.getId());
            Intent intent = new Intent(getContext(), TournamentPageActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

        gameName = (TextView) findViewById(R.id.game_name);
        setGameName(tournament.getGame());

        name = (TextView) findViewById(R.id.name_text);
        setName(tournament.getName());

        dateText = (TextView) findViewById(R.id.date_text);
        setDateText(tournament);
    }

    /**
     * function to set the tournament banner
     *
     * @param tournament the tournamentModel
     */
    private void setTournamentBanner(TournamentModel tournament) {
        new DownloadImageWorker(image -> tournamentBanner.setImageBitmap(image)).execute(tournament.getBanner());
    }

    /**
     * Function to set the name of the game
     *
     * @param name the name to set the game to
     */
    private void setGameName(String name) {
        gameName.setText(name);
    }

    /**
     * Function to set the name
     *
     * @param name the name to set the name to
     */
    private void setName(String name) {
        this.name.setText(name);
    }

    /**
     * Function to set the date of a tournament.
     *
     * @param tournament the tournament to set the date for.
     */
    private void setDateText(TournamentModel tournament) {
        dateText.setText(tournament.getStartDate() + " - " + tournament.getEndDate());
    }
}
