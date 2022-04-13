package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.ImageLoader;
import co.tournam.api.TournamentHandler;
import co.tournam.api.UploadImageWorker;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.StageOption;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.stageoptions.StageOptionColorPicker;
import co.tournam.ui.title.DefaultTitle;
import yuku.ambilwarna.AmbilWarnaDialog;


public class CreateTournamentActivity extends AppCompatActivity {

    Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout tournamentNameLayout;
    private LinearLayout tournamentGameNameLayout;
    private LinearLayout tournamentTeamSizeLayout;
    private LinearLayout tournamentColorPickerLayout;
    private LinearLayout stageOptionLayout;
    private LinearLayout createButtonLayout;
    private LinearLayout addButtonLayout;
    private LinearLayout backButtonLayout;
    private LinearLayout locationLayout;

    private String bannerID = "";
    private boolean isOnline;
    private String name;
    private int color;
    private String game;
    private int teamSize;
    private boolean isPublic;
    private int gameLength;
    private List<TournamentModel.CreateStageModel> stages;
    private String location;

    private CheckBox onlineBox;
    private CheckBox publicBox;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Bitmap bannerImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        context = this.getApplicationContext();
        this.stages = new ArrayList<>();

        // Open Gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            bannerImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            new UploadImageWorker(imageId -> this.bannerID = imageId).execute(bannerImage);
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
            createTournament();
            startActivity(new Intent(CreateTournamentActivity.this, DiscoveryActivity.class));
        });

        header.addView(new SmallHeader(context, "Create Tournament", () -> finish(), button));

        setTournamentBanner();
        setTournamentName();
        setTournamentGameName();
        setTournamentTeamSize();
        setTournamentColorPicker();
        setFirstHeader();
        setStageOption();
        setSecondHeader();
        setAddButton();
        setLocation();

        onlineBox = findViewById(R.id.isOnline_checkbox_create_tournament);
        publicBox = findViewById(R.id.isPublic_checkbox_create_tournament);

        
    }
    
    public void createTournament() {
        if (this.isOnline) {
            TournamentHandler.createOnline(this.bannerID, this.name, this.color, this.game, this.teamSize,
                    this.isPublic, this.gameLength, this.stages, new TournamentHandler.CreateComplete() {
                @Override
                public void success(String id) {
                    Toast.makeText(context, "Online Tournament Created", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    Toast.makeText(context, "Creation Error", Toast.LENGTH_LONG).show();
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        } else {
            TournamentHandler.createOffline(this.bannerID, this.name, this.color, this.game, this.teamSize,
                    this.isPublic, this.gameLength, this.location, this.stages, new TournamentHandler.CreateComplete() {
                @Override
                public void success(String id) {
                    Toast.makeText(context, "Offline Tournament Created", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    Toast.makeText(context, "Creation Error", Toast.LENGTH_LONG).show();
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });

        }
    }

    public void infoUpdate() {

        this.name = ((StageOptionBody) tournamentNameLayout.getChildAt(0)).getEntry();

        this.game = ((StageOptionBody) tournamentGameNameLayout.getChildAt(0)).getEntry();

        this.teamSize = Integer.parseInt(((StageOptionBody) tournamentTeamSizeLayout.getChildAt(0)).getEntry());

        this.location = ((StageOptionBody) locationLayout.getChildAt(0)).getEntry();

        for (int i = 0; i < stageOptionLayout.getChildCount(); i++) {
            this.stages.add(((StageOption) stageOptionLayout.getChildAt(i)).createStageFromEntry());
        }

        //TODO Change once we understand what game length means
        this.gameLength = 20;

        this.isOnline = onlineBox.isChecked();
        this.isPublic = publicBox.isChecked();

        Log.w("Updated:", "True");
        Log.w("Banner ID:", this.bannerID);
        Log.w("Name:", this.name);
        Log.w("Color:", String.valueOf(this.color));
        Log.w("Game:", this.game);
        Log.w("Team Size:", String.valueOf(this.teamSize));
        Log.w("Location:", this.location);
        Log.w("Stage Count:", String.valueOf(this.stages.size()));
        Log.w("Game Length", String.valueOf(this.gameLength));
        Log.w("isOnline:", String.valueOf(this.isOnline));
        Log.w("isPublic:", String.valueOf(this.isPublic));

    }


    public void setTournamentBanner() {
        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner_create_tournament);
        ImageListItem image;
        if (this.bannerID.equals("")) {
            image = new ImageListItem(context, BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.imagelist_add_plus));
        } else {
            image = new ImageListItem(context, ImageLoader.loadImage(this.bannerID, context));
        }

        DefaultButton selectImage = new DefaultButton(context, "Upload");
        tournamentBannerLayout.addView(image);
        tournamentBannerLayout.addView(selectImage);
        selectImage.button.setOnClickListener(v -> {
            openGallery();
        });

    }



    public void setTournamentName() {

        tournamentNameLayout = (LinearLayout) findViewById(R.id.tournamentName_create_tournament);
        tournamentNameLayout.addView( new StageOptionBody(
                context,
                "Name"));
    }

    public void setTournamentGameName() {

        tournamentGameNameLayout = (LinearLayout) findViewById(R.id.tournamentGameName_create_tournament);
        tournamentGameNameLayout.addView( new StageOptionBody(
                context,
                "Game Name"));
    }

    public void setTournamentTeamSize() {

        tournamentTeamSizeLayout = (LinearLayout) findViewById(R.id.tournamentTeamSize_create_tournament);
        tournamentTeamSizeLayout.addView( new StageOptionBody(
                context,
                "Team Size"));
    }

    public void setTournamentColorPicker() {

        tournamentColorPickerLayout = (LinearLayout) findViewById(R.id.tournamentColorPicker_create_tournament);
        StageOptionColorPicker colorPicker = new StageOptionColorPicker(context);
        colorPicker.colorButton.setOnClickListener(v -> {
            openColorPicker();
        });

        tournamentColorPickerLayout.addView(colorPicker);
    }

    public void openColorPicker() {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, R.color.gray_400, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                ((StageOptionColorPicker) tournamentColorPickerLayout.getChildAt(0)).setColor(color);
                setGlobalColor(color);
            }
        });
        ambilWarnaDialog.show();
    }

    private void setGlobalColor(int color) {
        this.color = color;
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne_create_tournament);
        firstHeaderLayout.addView( new DefaultTitle("Stages", context));
    }

    public void setStageOption() {

        stageOptionLayout = (LinearLayout) findViewById(R.id.stageOptions_create_tournament);
        StageOption stage = new StageOption(context);
        stageOptionLayout.addView(stage);

    }

    public void addStageOption() {

        StageOption stage = new StageOption(context);
        stageOptionLayout.addView(stage);

    }

    public void setSecondHeader() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.headerTwo_create_tournament);
        secondHeaderLayout.addView( new DefaultTitle("Location", context));
    }

    public void setAddButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        addButtonLayout = (LinearLayout) findViewById(R.id.addStageOptionButton_create_tournament);
        DefaultButton button = new DefaultButton(context, "Add");
        addButtonLayout.addView(button , 0);

        button.button.setOnClickListener(v -> {
            addStageOption();
        });

    }

    public void setLocation() {
        locationLayout = findViewById(R.id.location_create_tournament);
        locationLayout.addView(new StageOptionBody(context, "Location"));
        ((StageOptionBody) locationLayout.getChildAt(0)).setEntryHint("If not online...");
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}
