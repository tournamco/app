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

import co.tournam.api.ApiErrors;
import co.tournam.api.TeamHandler;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.models.TournamentModel;
import co.tournam.models.UserModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.StageOptionBody;

public class CreateTeamActivity extends AppCompatActivity {

    private Context context;

    private String tournamentId;
    private String organizerID;
    private Bitmap iconImage;
    private LinearLayout iconLayout;
    private LinearLayout nameLayout;

    private String iconId;
    private String teamName;
    private boolean isPublic;
    private boolean willJoin = true;
    private CheckBox joinBox;

    ActivityResultLauncher<Intent> someActivityResultLauncher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        context = this.getApplicationContext();

        tournamentId = getIntent().getStringExtra("tournamentid");

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

        TournamentHandler.info(tournamentId, new TournamentHandler.InfoComplete() {
            @Override
            public void success(TournamentModel tournament) {
                setOrganizer(tournament.getOrganizer().getId());
                compareWithMe();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void setOrganizer(String id) {
        this.organizerID = id;
    }

    private void compare(String id) {
        if (this.organizerID.equals(id)) {
            setJoinCheckBox();
        }
    }

    private void setJoinCheckBox() {
        joinBox.setVisibility(View.VISIBLE);
    }

    private void compareWithMe() {
        UserHandler.me(new UserHandler.MeCompleted() {
            @Override
            public void success(UserModel me) {
                compare(me.getId());
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void createTeam() {
        if (iconId != null) {
            willJoin = joinBox.isChecked();
            TeamHandler.createNormalTeam(tournamentId, willJoin, iconId, isPublic, teamName, new TeamHandler.CreateNormalTeamComplete() {
                @Override
                public void success(String teamId) {
                    Toast.makeText(context, "Team Created", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamid", teamId);
                    Intent intent = new Intent(context, OurTeamActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        } else {
            Toast.makeText(this, "Invalid Icon, Try Again", Toast.LENGTH_LONG).show();
        }

    }

    private void setIconField() {
        iconLayout = findViewById(R.id.icon);
        ImageListItem image = new ImageListItem(context, BitmapFactory.decodeResource(context.getResources(),
                R.drawable.imagelist_add_plus));
        DefaultButton selectImage = new DefaultButton(context, "Upload");
        iconLayout.addView(image);
        iconLayout.addView(selectImage);
        selectImage.button.setOnClickListener(v -> openGallery());
    }

    private void setNameField() {
        nameLayout = findViewById(R.id.name);
        nameLayout.addView(new StageOptionBody(context, "Name", InputType.TYPE_CLASS_TEXT));
    }

    private void infoUpdate() {
        teamName = ((StageOptionBody) nameLayout.getChildAt(0)).getEntry();
        isPublic = ((CheckBox) findViewById(R.id.is_public)).isChecked();
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}
