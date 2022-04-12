package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.imagelist.ImageListHorizontal;
import co.tournam.ui.table.Table;
import co.tournam.ui.table.TableRow;
import co.tournam.ui.team_members.TeamMembers;
import co.tournam.ui.teamscore.TeamScore;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class MatchDetailActivity extends AppCompatActivity {

    private final String tournamentID;
    private final String matchID;
    Context context;
    private LinearLayout tournamentLogoLayout;
    private LinearLayout teamScoreLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout tableLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout membersLayout;
    private LinearLayout thirdHeaderLayout;
    private LinearLayout proofOfScoreLayout;

    private Bitmap bm;
    private List<Bitmap> proofs = new ArrayList<>();

    ActivityResultLauncher<Intent> someActivityResultLauncher;

    public MatchDetailActivity(String tournamentID, String matchID) {
        this.tournamentID = tournamentID;
        this.matchID = matchID;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        context = this.getApplicationContext();

        // Open Gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            String id = ImageLoader.uploadImage(bm);
                            proofs.add(bm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED)  {
                    }
                });

        setTournamentLogoLayout();
        setTeamScoreLayout();
        setFirstHeaderLayout();
        setTableLayout();
        setSecondHeaderLayout();
        setMembersLayout();
        setThirdHeaderLayout();
        setProofOfScoreLayout();
    }

    public void setTournamentLogoLayout() {
        tournamentLogoLayout = (LinearLayout) findViewById(R.id.tournamentLogo_match_details);

        TournamentHandler.info(this.tournamentID, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                tournamentLogoLayout.addView( new TournamentSummaryListItem(
                        context,
                        tournament));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setTeamScoreLayout() {
        teamScoreLayout = (LinearLayout) findViewById(R.id.matchPoints);

        TeamHandler.matchInfo(this.matchID, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel match) {
                teamScoreLayout.addView( new TeamScore(context, match));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setFirstHeaderLayout() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.first_header_information);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.INFORMATION
        ));
    }

    public void setTableLayout() {
        tableLayout = (LinearLayout) findViewById(R.id.information_table);

        TeamHandler.matchInfo(this.matchID, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel match) {
                tableLayout.addView(setUpTable(context, match));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });



    }

    public Table setUpTable(Context context, MatchModel match) {

        Table matchInfo = new Table(this.context, 4);
        setTableRow(matchInfo, 0, "Name", match.getName());
        setTableRow(matchInfo, 1, "Start Date", match.getStartDate().toString());
        setTableRow(matchInfo, 2, "End Data", match.getEndDate().toString());
        setTableRow(matchInfo, 3, "Games?", match.getGames().toString()); //TODO The f?

        return matchInfo;
    }

    public void setTableRow(Table table, int index, String title, String data) {

        ((TableRow) table.getChildAt(index)).setTitleText(title);
        ((TableRow) table.getChildAt(index)).setDataText(data);
    }

    public void setSecondHeaderLayout() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.second_header_teams);
        secondHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.TEAMS
        ));
    }

    public void setMembersLayout() {
        membersLayout = (LinearLayout) findViewById(R.id.team_members);
        List<TeamModel> teams = new ArrayList<>();

        TeamHandler.matchInfo(this.matchID, new TeamHandler.MatchInfoComplete() {
            @Override
            public void success(MatchModel match) {
                teams.add(match.getTeams().get(0));
                teams.add(match.getTeams().get(1));
                membersLayout.addView(new TeamMembers(context, teams));
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setThirdHeaderLayout() {
        thirdHeaderLayout = (LinearLayout) findViewById(R.id.third_header_proof_of_score);
        thirdHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.PROOF_OF_SCORE
        ));
    }

    public void setProofOfScoreLayout() {
        proofOfScoreLayout = (LinearLayout) findViewById(R.id.proof_of_score);


        ImageListHorizontal imageList = new ImageListHorizontal(context, this.proofs);
        DefaultButton addImage = new DefaultButton(context, "Add Proof");

        proofOfScoreLayout.addView(imageList);
        proofOfScoreLayout.addView(addImage);

        addImage.button.setOnClickListener(v -> {
            // Perform action on click
            openGallery();
        });

    }



    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }


}
