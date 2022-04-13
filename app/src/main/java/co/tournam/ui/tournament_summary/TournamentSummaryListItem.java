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
    private TextView participants;
    private TextView name;
    private TextView dateText;
    private Button button;
    private TextView location;

    public TournamentSummaryListItem(Context context, TournamentModel tournament) {
        super(context);

        this.tournament = tournament;

        build(context);

    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tournament_summary, this, true);

        tournamentBanner = (ImageView)findViewById(R.id.banner);
        setTournamentBanner(tournament);
        tournamentBanner.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tournamentid", tournament.getId());
            Intent intent = new Intent(getContext(), TournamentPageActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

        gameName = (TextView)findViewById(R.id.game_name);
        setGameName(tournament.getGame());

        name = (TextView)findViewById(R.id.name_text);
        setName(tournament.getName());

        dateText = (TextView)findViewById(R.id.date_text);
        setDateText(tournament);
    }

    private void setTournamentBanner(TournamentModel tournament) {
        new DownloadImageWorker(image -> tournamentBanner.setImageBitmap(image)).execute(tournament.getBanner());
    }

    private void setGameName(String name) {
        gameName.setText(name);
    }

    private void setName(String name) {
        this.name.setText(name);
    }

    private void setDateText(TournamentModel tournament) {
        dateText.setText(tournament.getStartDate() + " - " + tournament.getEndDate());
    }
}
