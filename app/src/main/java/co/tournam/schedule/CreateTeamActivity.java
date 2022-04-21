package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.StageOptionBody;

public class CreateTeamActivity extends AppCompatActivity {

    //Variable Declarations
    private Context context;
    private String tournamentId;
    private String organizerID;
    private String iconId;
    private String teamName;
    private Bitmap iconImage;
    private LinearLayout iconLayout;
    private LinearLayout nameLayout;
    private boolean isPublic;
    private boolean willJoin = true;
    private CheckBox joinBox;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    //On create method of the Create Team Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        context = this.getApplicationContext();

        tournamentId = getIntent().getStringExtra("tournamentid");

        //Opens gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            iconImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            new UploadImageWorker(imageId -> this.iconId = imageId).execute(iconImage);
                            ((ImageListItem) iconLayout.getChildAt(0)).setImage(iconImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                });

        LinearLayout header = findViewById(R.id.header);
        joinBox = findViewById(R.id.join_box);
        DefaultButtonFilled button = new DefaultButtonFilled(context, "Create");

        button.button.setOnClickListener(v -> {
            infoUpdate();
            createTeam();
        });

        header.addView(new SmallHeader(context, "Create Team", () -> finish(), button));

        setIconField();
        setNameField();

        TournamentHandler.info(tournamentId, tournament -> {
            setOrganizer(tournament.getOrganizer().getId());
            compareWithMe();
        });
    }

    private void setOrganizer(String id) {
        this.organizerID = id;
    }

    //Compares the passed in ID to the known organizer's ID
    private void compare(String id) {
        if (this.organizerID.equals(id)) {
            setJoinCheckBox();
        }
    }

    private void setJoinCheckBox() {
        joinBox.setVisibility(View.VISIBLE);
    }

    //Passes the current Users ID to the compare() method
    private void compareWithMe() {
        UserHandler.me(me -> compare(me.getId()));
    }

    //Creates a team from the collected information
    private void createTeam() {
        if (iconId != null) {
            willJoin = joinBox.isChecked();
            TeamHandler.createNormalTeam(tournamentId, willJoin, iconId, isPublic, teamName, teamId -> {
                Toast.makeText(context, "Team Created", Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("teamid", teamId);
                Intent intent = new Intent(context, OurTeamActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        } else {
            Toast.makeText(this, "Invalid Icon, Try Again", Toast.LENGTH_LONG).show();
        }
    }

    //Sets and builds the team icon field
    private void setIconField() {
        iconLayout = findViewById(R.id.icon);
        ImageListItem image = new ImageListItem(context, BitmapFactory.decodeResource(context.getResources(),
                R.drawable.imagelist_add_plus));
        DefaultButton selectImage = new DefaultButton(context, "Upload");
        iconLayout.addView(image);
        iconLayout.addView(selectImage);
        selectImage.button.setOnClickListener(v -> openGallery());
    }

    //Sets and builds the team name field
    private void setNameField() {
        nameLayout = findViewById(R.id.name);
        nameLayout.addView(new StageOptionBody(context, "Name", InputType.TYPE_CLASS_TEXT));
    }

    //Update the values of the global variables based on the entered information by the user
    private void infoUpdate() {
        teamName = ((StageOptionBody) nameLayout.getChildAt(0)).getEntry();
        isPublic = ((CheckBox) findViewById(R.id.is_public)).isChecked();
    }

    //Calls intent to open the phone's gallery
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}
