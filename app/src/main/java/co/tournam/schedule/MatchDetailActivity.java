package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ProofHandler;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UserHandler;
import co.tournam.models.GameModel;
import co.tournam.models.MatchModel;
import co.tournam.models.ProofModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListHorizontal;
import co.tournam.ui.table.Table;
import co.tournam.ui.table.TableRow;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.teamscore.TeamScore;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class MatchDetailActivity extends AppCompatActivity {

    //Variable Declarations
    private Context context;
    private ImageListHorizontal imageList;
    private TournamentModel tournament;
    private MatchModel match;
    private UserModel me;
    private String teamKey;

    //On create method of the Match Detail Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        context = this.getApplicationContext();

        String matchId = getIntent().getStringExtra("matchid");

        loadMatch(matchId);
    }

    //On resume, the activity checks if the match is finished and updates the text views accordingly
    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout addProofButtonLayout = (LinearLayout) findViewById(R.id.proof_of_score_button);
        if (match != null && teamKey != null && match.getFinished().get(teamKey)) {
            addProofButtonLayout.removeAllViews();
            TextView alreadyFinished = new TextView(context);
            alreadyFinished.setText("Already finished");
            addProofButtonLayout.addView(alreadyFinished);
        }
    }

    /**
     * Loads and sets the information of a match with its given ID.
     *
     * @param matchId id of the match
     * @post this.match = MatchModel match
     */
    private void loadMatch(String matchId) {
        TeamHandler.matchInfo(matchId, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel match) {
                MatchDetailActivity.this.match = match;
                loadTournament(match.getTournament().getId());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    /**
     * Loads and sets the information of a tournament with its given ID.
     *
     * @param tournamentId id of the tournament
     * @post this.tournament = TournamentModel tournament
     */
    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                MatchDetailActivity.this.tournament = tournament;
                loadMe();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    //Loads and sets the information of the user and then builds the activity
    private void loadMe() {
        UserHandler.me(new UserHandler.MeCompleted() {
            @Override
            public void success(UserModel user) {
                MatchDetailActivity.this.me = user;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    //Builds the components of the activity
    private void build() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'th of' MMMM, HH:mm");
        LinearLayout headerContainer = (LinearLayout) findViewById(R.id.header);
        headerContainer.addView(new SmallHeader(context, formatter.format(match.getStartDate()), match.getName(), () -> finish()));

        LinearLayout tournamentBannerLayout = (LinearLayout) findViewById(R.id.banner);
        tournamentBannerLayout.addView(new TournamentSummaryListItem(context, tournament));

        setTeamScoreLayout();

        LinearLayout membersTitleLayout = (LinearLayout) findViewById(R.id.information_title);
        membersTitleLayout.addView(new DefaultTitle("Information", context));

        LinearLayout tableLayout = (LinearLayout) findViewById(R.id.information_table);
        tableLayout.addView(setUpTable(context, match));

        LinearLayout teamsTitleLayout = (LinearLayout) findViewById(R.id.teams_title);
        teamsTitleLayout.addView(new DefaultTitle("Teams", context));

        setMembersLayout();

        if (isLeaderInMatch()) {
            LinearLayout proofOfScoreTitleLayout = (LinearLayout) findViewById(R.id.proof_of_score_title);
            proofOfScoreTitleLayout.addView(new DefaultTitle("Proof of score", context));

            setProofOfScoreLayout();
        }
    }

    //Checks whether the current user is the leader of a team participating in the match
    public boolean isLeaderInMatch() {
        for (Map.Entry<String, TeamModel> entry : match.getTeams().entrySet()) {
            if (entry.getValue() == null || entry.getValue().getLeader() == null
                    || !entry.getValue().getLeader().getId().equals(me.getId())) {
                continue;
            }

            this.teamKey = entry.getKey();

            return true;
        }

        return false;
    }

    //Sets and builds the match score component
    public void setTeamScoreLayout() {
        LinearLayout teamScoreLayout = (LinearLayout) findViewById(R.id.score);
        teamScoreLayout.addView(new TeamScore(context, match));
    }

    //Sets up the table containing the match information
    public Table setUpTable(Context context, MatchModel match) {
        Table matchInfo = new Table(this.context, 3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        setTableRow(matchInfo, 0, "Start date", formatter.format(match.getStartDate()));
        setTableRow(matchInfo, 1, "End date", formatter.format(match.getEndDate()));
        setTableRow(matchInfo, 2, "Format", "Best of " + match.getGames().size());

        return matchInfo;
    }

    //Adds a row to the match information table
    public void setTableRow(Table table, int index, String title, String data) {
        ((TableRow) table.getChildAt(index)).setTitleText(title);
        ((TableRow) table.getChildAt(index)).setDataText(data);
    }

    //Sets and builds the component displaying the members of the teams
    public void setMembersLayout() {
        LinearLayout membersLayout = (LinearLayout) findViewById(R.id.team_members);
        List<TeamModel> teams = match.getTeams().values().stream().collect(Collectors.toList());
        membersLayout.addView(new TeamMembers(context, teams));
    }

    //Sets and builds the component displaying the proofs of score and the button to edit the proofs
    public void setProofOfScoreLayout() {
        LinearLayout addProofButtonLayout = (LinearLayout) findViewById(R.id.proof_of_score_button);
        if (match.getFinished().get(teamKey)) {
            TextView alreadyFinished = new TextView(context);
            alreadyFinished.setText("Already finished");
            addProofButtonLayout.addView(alreadyFinished);
        } else {
            DefaultButton button = new DefaultButton(context, "Add proof");
            button.button.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("matchid", match.getId());
                bundle.putString("teamkey", teamKey);
                Intent intent = new Intent(context, MatchProofActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            addProofButtonLayout.addView(button);
        }

        LinearLayout proofListLayout = (LinearLayout) findViewById(R.id.proof_of_score_list);
        imageList = new ImageListHorizontal(context, new ArrayList<>());
        proofListLayout.addView(imageList);

        getProofIds(match.getGames());
    }

    //Returns the IDs of the proofs of the games of the match
    public void getProofIds(List<GameModel> games) {
        for (GameModel game : games) {
            if (game.getProofs().get(teamKey) == null) continue;

            ProofHandler.info(game.getProofs().get(teamKey), new ProofHandler.InfoComplete() {
                @Override
                public void success(ProofModel proof) {
                    loadImages(proof.getImages());
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        }
    }

    //Loads the images from the passed in IDs and sets them and adds them to the global image list
    public void loadImages(List<String> images) {
        for (String imageId : images) {
            new DownloadImageWorker(image -> imageList.addImage(image)).execute(imageId);
        }
    }
}
