package co.tournam.ui.tournament_summary;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.api.DownloadImageWorker;
import co.tournam.models.TournamentModel;
import co.tournam.schedule.JoinTournamentActivity;
import co.tournam.schedule.R;
import co.tournam.schedule.TournamentPageActivity;

public class TournamentSummaryListJoinItem extends LinearLayout {

    private TournamentModel tournament;
    private ImageView tournamentBanner;
    private TextView gameName;
    private TextView participants;
    private TextView name;
    private TextView dateText;
    private Button button;
    private TextView location;
    private TournamentModel.TournamentLocation locationModel;

    public TournamentSummaryListJoinItem(Context context, TournamentModel tournament, TournamentModel.TournamentLocation location) {
        super(context);

        this.tournament = tournament;
        this.locationModel = location;

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

        tournamentBanner = (ImageView)findViewById(R.id.banner);
        setTournamentBanner(tournament);
        tournamentBanner.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tournamentid", tournament.getId());
            Intent intent = new Intent(getContext(), TournamentPageActivity.class).setFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        gameName = (TextView)findViewById(R.id.game_name);
        setGameName(tournament.getGame());

        name = (TextView)findViewById(R.id.name_text);
        setName(tournament.getName());

        dateText = (TextView)findViewById(R.id.date_text);
        setDateText(tournament);

        participants = (TextView)findViewById(R.id.participants_text);
        setParticipants(tournament);

        location = (TextView)findViewById(R.id.location_text);

        if(tournament.isOnline()) {
            location.setVisibility(GONE);
        }
        else {
            setLocation(tournament);
        }

        button = (Button)findViewById(R.id.join_button);
        button.setText("Join");
        button.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("tournamentid", tournament.getId());
            Intent intent = new Intent(context, JoinTournamentActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent.setFlags(FLAG_ACTIVITY_NEW_TASK));
        });
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

    private void setParticipants(TournamentModel tournament) {
        participants.setText(
            tournament.getCurrentAmountOfTeams() + " / " +
            tournament.getStages().get(0).getNumberOfParticipants() + " participants");
    }

    private void setDateText(TournamentModel tournament) {
        dateText.setText(tournament.getStartDate() + " - " + tournament.getEndDate());
    }

    private void setLocation(TournamentModel tournament) {
        double distance = this.distance(locationModel.getLatitude(), locationModel.getLongitude(),
                tournament.getLocation().getLongitude(), tournament.getLocation().getLatitude(), 'K');
        System.out.println(locationModel.getLatitude() + " " + locationModel.getLongitude() + " " + tournament.getLocation().getLatitude() + " " + tournament.getLocation().getLongitude());

        location.setText(String.format("%.2f", distance) + " kilometers away");
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
