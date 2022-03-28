package co.tournam.ui.tournament_summary;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.models.TournamentModel;
import co.tournam.schedule.R;

public class TournamentSummaryListJoinItem extends LinearLayout{

    private TournamentModel tournament;
    private ImageButton tournamentBanner;
    private ImageView icon;
    private TextView gameName;
    private TextView participants;
    private Button button;
    private TextView location;

    public TournamentSummaryListJoinItem(Context context, TournamentModel tournament) {
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
        inflater.inflate(R.layout.tournament_summary_button, this, true);

        tournamentBanner = (ImageButton)findViewById(R.id.banner);
        SetTournamentBanner(tournament);

        icon = (ImageView)findViewById(R.id.icon);
        SetIcon(tournament);

        gameName = (TextView)findViewById(R.id.gameName);
        SetName(tournament);

        participants = (TextView)findViewById(R.id.participants);
        setParticipants(tournament);

        location = (TextView)findViewById(R.id.locationText);
        setLocation(tournament);

        button = (Button)findViewById(R.id.joinButton);
        button.setText("Join");
    }

    private void SetTournamentBanner(TournamentModel tournament)
    {tournamentBanner.setImageResource(tournament.getImageID());}

    private void SetIcon(TournamentModel tournament)
    {icon.setImageResource(tournament.getTournamentIcon());}

    private void SetName(TournamentModel tournament)
    {gameName.setText(tournament.getGame());}

    private void setParticipants(TournamentModel tournament)
    {participants.setText(tournament.getCurrentAmountOfTeams() + " / " +
            tournament.getTournamentLimit());}

    private void setLocation(TournamentModel tournament) { location.setText(tournament.getLocation());}
}