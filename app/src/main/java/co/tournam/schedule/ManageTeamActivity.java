package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.models.TeamModel;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.list.UserList;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.title.DefaultTitle;
import co.tournam.ui.title.SubtextTitle;

public class ManageTeamActivity extends AppCompatActivity {

    //Variable Declarations
    Context context;
    private LinearLayout buttonsLayout;
    private LinearLayout membersLayout;
    private TeamModel team;
    private TournamentModel tournament;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Bitmap iconImage;
    private LinearLayout userIconLayout;

    //On create method of the Manage Team Activity calling and setting up functions and variables
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
                            ((ImageListItem) userIconLayout.getChildAt(0)).setImage(iconImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                });

        loadTeam(teamId);
    }

    /**
     * Loads and sets the information of a team with its given ID.
     *
     * @param teamId id of the team
     * @post this.team = TeamModel team
     */
    private void loadTeam(String teamId) {
        TeamHandler.info(teamId, team -> {
            ManageTeamActivity.this.team = team;
            loadTournament(team.getTournamentId());
        });
    }

    /**
     * Loads and sets the information of a tournament with its given ID.
     *
     * @param tournamentId id of the tournament
     * @post this.tournament = TournamentModel tournament
     */
    private void loadTournament(String tournamentId) {
        TournamentHandler.info(tournamentId, tournament -> {
            ManageTeamActivity.this.tournament = tournament;
            build();
        });
    }

    //Builds the components of the activity
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
            TeamHandler.delete(team.getID(), () ->
                    startActivity(new Intent(ManageTeamActivity.this, ScheduleActivity.class)));
        });
    }

    //Sets and builds the team icon
    public void setIconLayout() {
        userIconLayout = (LinearLayout) findViewById(R.id.team_icon_image);

        ImageListItem image = new ImageListItem(context, null);
        userIconLayout.addView(image);

        try {
            new DownloadImageWorker(bitmap -> image.setImage(bitmap)).execute(team.getIcon());
        } catch (NullPointerException e) {
            Log.w("User Icon is null", e);
        }

        ImageButton change = (ImageButton) findViewById(R.id.team_icon_button);
        change.setOnClickListener(v -> {
            openGallery();
        });
    }

    //Changes the team icon to the passed in icon id
    public void changeIcon(String iconId) {
        TeamHandler.changeIcon(iconId, () ->
                Toast.makeText(ManageTeamActivity.this, "Icon changed.", Toast.LENGTH_SHORT).show());
    }

    //Sets and builds the team name field
    public void setTeamName() {
        LinearLayout nameLayout = (LinearLayout) findViewById(R.id.name_input);
        StageOptionBody body = new StageOptionBody(context, "Name", InputType.TYPE_CLASS_TEXT);
        body.setEntryText(team.getName());
        nameLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.name_button);
        change.setOnClickListener(v -> {
            TeamHandler.changeName(((StageOptionBody) nameLayout.getChildAt(0)).getEntry(),
                    () -> Toast.makeText(ManageTeamActivity.this, "Name changed.", Toast.LENGTH_SHORT).show());
        });
    }

    //Sets up the buttons to using qr code or nfc to invite a team member
    private void setButtons() {
        buttonsLayout = (LinearLayout) findViewById(R.id.buttons);
        DefaultButton qrButton = new DefaultButton(context, "Use QR");
        DefaultButton nfcButton = new DefaultButton(context, "Use NFC");
        buttonsLayout.addView(qrButton);
        buttonsLayout.addView(nfcButton);

        qrButton.button.setOnClickListener(v -> {

            TeamHandler.createInvite(tournament.getId(), team.getID(), token -> {
                Bundle b = new Bundle();
                b.putString("token", token);
                Intent intent = new Intent(context, QRGenActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            });

        });
    }

    //Sets and builds the list of team members
    private void setMembers() {
        membersLayout = (LinearLayout) findViewById(R.id.members);
        membersLayout.addView(new UserList(context, team.getMembers(), "Remove", (user) -> {
            TeamHandler.leave(team.getID(), user.getId(), () -> {
                membersLayout.removeAllViews();
                setMembers();
            });
        }));
    }

    //Calls an intent to open the user's gallery
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}



