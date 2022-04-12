package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.IOException;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.models.members.Members;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.list.UserList;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.textentry.TextEntry;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.title.SubtextTitle;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

public class ManageTeamActivity extends AppCompatActivity {

    Context context;
    private LinearLayout buttonsLayout;
    private LinearLayout membersLayout;
    private TeamModel team;
    private TournamentModel tournament;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Bitmap iconImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String teamId = null;

        if (b != null) {
            teamId = b.getString("teamid");
        }

        // Open Gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            iconImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            new UploadImageWorker(id -> changeIcon(id)).execute(iconImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                });

        loadTeam(teamId);
    }

    private void loadTeam(String teamId) {
        TeamHandler.info(teamId, new TeamHandler.InfoComplete() {
            @Override
            public void success(TeamModel team) {
                ManageTeamActivity.this.team = team;
                loadTournament(team.getTournamentId());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                ManageTeamActivity.this.tournament = tournament;
                build();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void build() {
        setContentView(R.layout.activity_manage_team);
        context = this.getApplicationContext();

        LinearLayout headerLayout = findViewById(R.id.header);
        headerLayout.addView(new SmallHeader(context, "Manage Team", () -> finish()));

        setIconLayout();
        setTeamName();

        LinearLayout inviteTitle = (LinearLayout) findViewById(R.id.invite_title);
        inviteTitle.addView(new DefaultTitle("Invite", context));

        setButtons();

        LinearLayout membersTitle = (LinearLayout) findViewById(R.id.members_title);
        membersTitle.addView(new SubtextTitle("Members", team.getMembers().size() + "/" + tournament.getTeamSize(), context));

        setMembers();

        Button abandonButton = (Button) findViewById(R.id.abandon_button);
        abandonButton.setOnClickListener(view -> {
            // TODO: Implement tournament abandon api call
        });
    }

    public void setIconLayout() {
        LinearLayout userIconLayout = (LinearLayout) findViewById(R.id.team_icon_image);

        ImageListItem image = new ImageListItem(context, null);
        userIconLayout.addView(image);

        try{
            new DownloadImageWorker(bitmap -> image.setImage(bitmap)).execute(team.getIcon());
        } catch (NullPointerException e) {
            Log.w("User Icon is null", e);
        }

        ImageButton change = (ImageButton) findViewById(R.id.team_icon_button);
        change.setOnClickListener(v -> {
            openGallery();
        });
    }

    public void changeIcon(String iconId) {
        TeamHandler.changeIcon(iconId, new TeamHandler.ChangeComplete() {
            @Override
            public void success() {
                Toast.makeText(ManageTeamActivity.this, "Icon changed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setTeamName() {
        LinearLayout nameLayout = (LinearLayout) findViewById(R.id.name_input);
        StageOptionBody body = new StageOptionBody(context, "Name");
        body.setEntryText(team.getName());
        nameLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.name_button);
        change.setOnClickListener(v -> {
            TeamHandler.changeName(((StageOptionBody) nameLayout.getChildAt(0)).getEntry(),
                    new TeamHandler.ChangeComplete() {
                        @Override
                        public void success() {
                            Toast.makeText(ManageTeamActivity.this, "Name changed.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(ApiErrors error, String message) {
                            System.err.println("API_ERROR: " + error.name() + " - " + message);
                        }
                    });
        });
    }

    private void setButtons() {
        buttonsLayout = (LinearLayout) findViewById(R.id.buttons);
        DefaultButton qrButton = new DefaultButton(context,"Use QR");
        DefaultButton nfcButton = new DefaultButton(context, "Use NFC");
        buttonsLayout.addView(qrButton);
        buttonsLayout.addView(nfcButton);
    }

    private void setMembers() {
        membersLayout = (LinearLayout) findViewById(R.id.members);
        membersLayout.addView(new UserList(context, team.getMembers(), "Remove", (user) -> {
            // TODO: Remove user from team api
        }));
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}



