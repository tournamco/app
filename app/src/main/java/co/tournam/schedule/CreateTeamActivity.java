package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TeamHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.StageOption;
import co.tournam.ui.stageoptions.StageOptionBody;

public class CreateTeamActivity extends AppCompatActivity {

    private Context context;

    private String tournamentId;
    private Bitmap iconImage;

    private LinearLayout nameLayout;

    private String iconId;
    private String teamName;
    private boolean isPublic;

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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED)  {
                    }
                });

        LinearLayout header = findViewById(R.id.header);
        DefaultButtonFilled button = new DefaultButtonFilled(context, "Create");

        button.button.setOnClickListener(v -> {
            infoUpdate();
            createTeam();
        });

        header.addView(new SmallHeader(context, "Create Team", () -> finish(), button));

        setIconField();
        setNameField();
    }

    private void createTeam() {
        TeamHandler.createNormalTeam(tournamentId, iconId, isPublic, teamName, new TeamHandler.CreateNormalTeamComplete() {
            @Override
            public void success(String teamId) {
                Toast.makeText(context, "Team Created", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    private void setIconField() {
        LinearLayout iconLayout = findViewById(R.id.icon);
        ImageListItem image = new ImageListItem(context, BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.imagelist_add_plus));
        DefaultButton selectImage = new DefaultButton(context, "Upload");
        iconLayout.addView(image);
        iconLayout.addView(selectImage);
        selectImage.button.setOnClickListener(v -> openGallery());
    }

    private void setNameField() {
        nameLayout = findViewById(R.id.name);
        nameLayout.addView( new StageOptionBody(context, "Name"));
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
