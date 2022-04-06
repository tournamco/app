package co.tournam.ui.tournamentpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Header;

import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;
import co.tournam.schedule.R;
import co.tournam.schedule.schedule.Tournament;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.header.headerTournament;
import co.tournam.ui.header.teamsInTournamentHeader;
import co.tournam.ui.list.TeamList;
import co.tournam.ui.matchlist.SummaryMatchList;
import co.tournam.ui.stagelist.StageList;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class TournamentPage extends AbstractTournamentPage{

    private ImageView tournamentBanner;
    private TournamentModel Tournament;
    private TextView gameName;
    private LinearLayout firstHeader;
    private LinearLayout stages;
    private LinearLayout secondHeader;
    private LinearLayout teams;
    private LinearLayout matchesHeader;
    private LinearLayout matchView;
    private List<MatchModel> matchModels;

    public TournamentPage(Context context, TournamentModel tournament) {
        super(context, tournament);
        this.tournament = tournament;
        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_tournament_page, this, true);
        setBanner(context);
        setGameName(context);
        firstHeader = findViewById(R.id.stageHeader);
        stages = findViewById(R.id.stages);
        firstHeader.addView(new header(context, null, headerTitle.STAGES));
        setStages(context);
        secondHeader = findViewById(R.id.teamsHeader);
        teams = findViewById(R.id.teams);
        secondHeader.addView(new teamsInTournamentHeader(context, tournament ));
        setTeams(context);
        matchesHeader = findViewById(R.id.matchesHeader);
        matchView = findViewById(R.id.matchView);
        matchesHeader.addView(new header(context, null, headerTitle.MATCHES));
        setMatches(context);
    }

    private void setBanner(Context context) {
        tournamentBanner = findViewById(R.id.tournamentBanner);
        tournamentBanner.setImageBitmap(ImageLoader.loadImage(tournament.getBanner(), context));
    }

    private void setGameName(Context context) {
        gameName = findViewById(R.id.text);
        gameName.setText(tournament.getGame());
    }

    private void setStages(Context context) {
        stages.removeAllViews();
        stages.addView(new StageList(context, (ArrayList<StageModel>) tournament.getStages(), tournament));
    }

    private void setTeams(Context context) {
        teams.removeAllViews();
        List<String> team = tournament.getTeams();
        teams.addView(new TeamList(context, team, "Remove"));
    }

    private void setMatches(Context context) {
        matchView.removeAllViews();
        TournamentHandler.listMatches(tournament.getId(),
                new TournamentHandler.ListMatchesComplete() {
                    @Override
                    public void success(List<MatchModel> matches) {
                        matchModels = matches;
                    }

                    @Override
                    public void failure(ApiErrors error, String message) {

                    }
                });
        matchView.addView(new SummaryMatchList(context, matchModels));
    }
}
