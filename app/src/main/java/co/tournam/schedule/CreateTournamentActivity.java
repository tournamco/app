package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    //Variable Declarations
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout tournamentNameLayout;
    private LinearLayout tournamentGameNameLayout;
    private LinearLayout tournamentTeamSizeLayout;
    private LinearLayout tournamentColorPickerLayout;
    private LinearLayout stageOptionLayout;
    private LinearLayout tournamentGameLengthLayout;
    private LinearLayout addButtonLayout;
    private LinearLayout locationLayout;
    private TextView numberOfWinners;
    private String name;
    private String bannerID = "";
    private String game;
    private String location = "no location";
    private boolean participantsHasError;
    private boolean isOnline = false;
    private boolean isPublic;
    private int color;
    private int teamSize;
    private int gameLength;
    private List<TournamentModel.CreateStageModel> stages;
    private CheckBox onlineBox;
    private CheckBox publicBox;
    private Bitmap bannerImage;

    /*
     * On create method of the Create Tournament Activity calling and setting up functions
     * and variables
     */
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
                            ((ImageListItem) tournamentBannerLayout.getChildAt(0)).setImage(bannerImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                });


        LinearLayout header = findViewById(R.id.header);
        DefaultButtonFilled button = new DefaultButtonFilled(context, "Create");

        button.button.setOnClickListener(v -> {
            if (participantsHasError) {
                Toast.makeText(context, "Number of participants is incorrect", Toast.LENGTH_LONG).show();

                return;
            }

            infoUpdate();
            createTournament();
            startActivity(new Intent(CreateTournamentActivity.this, DiscoveryActivity.class));
        });

        header.addView(new SmallHeader(context, "Create Tournament", () -> finish(), button));

        LinearLayout numberOfWinnersLayout = findViewById(R.id.numberOfWinners);
        numberOfWinners = new TextView(context);
        numberOfWinners.setText("Number of Winners is not set");
        numberOfWinnersLayout.addView(numberOfWinners);

        //Setting up components
        setTournamentBanner();
        setTournamentName();
        setTournamentGameName();
        setTournamentGameLength();
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

    //Creates a tournament from the collected information
    public void createTournament() {
        System.out.println("Tournament " + this.isOnline + " - " + onlineBox.isChecked());
        if (this.isOnline) {
            TournamentHandler.createOnline(this.bannerID, this.name, this.color, this.game, this.teamSize,
                    this.isPublic, this.gameLength, this.stages, id -> {
                        Toast.makeText(context, "Online Tournament Created", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    });
        } else {
            TournamentHandler.createOffline(this.bannerID, this.name, this.color, this.game, this.teamSize,
                    this.isPublic, this.gameLength, this.location, this.stages, id -> {
                        Toast.makeText(context, "Offline Tournament Created", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    });
        }
    }

    //Updates all variables of the activity based on the entries
    public void infoUpdate() {
        this.name = ((StageOptionBody) tournamentNameLayout.getChildAt(0)).getEntry();
        this.game = ((StageOptionBody) tournamentGameNameLayout.getChildAt(0)).getEntry();
        this.teamSize = Integer.parseInt(((StageOptionBody) tournamentTeamSizeLayout.getChildAt(0)).getEntry());
        this.gameLength = Integer.parseInt(((StageOptionBody) tournamentGameLengthLayout.getChildAt(0)).getEntry());

        for (int i = 0; i < stageOptionLayout.getChildCount(); i++) {
            this.stages.add(((StageOption) stageOptionLayout.getChildAt(i)).createStageFromEntry());
        }

        this.isOnline = onlineBox.isChecked();
        this.isPublic = publicBox.isChecked();
    }

    //Sets and builds the Tournament Banner field
    public void setTournamentBanner() {
        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner_create_tournament);
        ImageListItem image;

        if (this.bannerID.equals("")) {
            image = new ImageListItem(context, BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.imagelist_add_plus));
        } else {
            image = new ImageListItem(context, ImageLoader.loadImage(this.bannerID));
        }

        DefaultButton selectImage = new DefaultButton(context, "Upload");
        tournamentBannerLayout.addView(image);
        tournamentBannerLayout.addView(selectImage);
        selectImage.button.setOnClickListener(v -> {
            openGallery();
        });
    }

    //Sets and builds the Tournament Name field
    public void setTournamentName() {
        tournamentNameLayout = (LinearLayout) findViewById(R.id.tournamentName_create_tournament);
        tournamentNameLayout.addView(new StageOptionBody(context, "Name", InputType.TYPE_CLASS_TEXT));
    }

    //Sets and builds the Tournament Game Name field
    public void setTournamentGameName() {
        tournamentGameNameLayout = (LinearLayout) findViewById(R.id.tournamentGameName_create_tournament);
        tournamentGameNameLayout.addView(new StageOptionBody(context, "Game Name", InputType.TYPE_CLASS_TEXT));
    }

    //Sets and builds the Tournament Game Length field
    private void setTournamentGameLength() {
        tournamentGameLengthLayout = (LinearLayout) findViewById(R.id.tournamentGameLength_create_tournament);
        tournamentGameLengthLayout.addView(new StageOptionBody(
                context,
                "Game Length\n(in min.)", InputType.TYPE_CLASS_NUMBER));
    }

    //Sets and builds the Tournament Team Size field
    public void setTournamentTeamSize() {
        tournamentTeamSizeLayout = (LinearLayout) findViewById(R.id.tournamentTeamSize_create_tournament);
        tournamentTeamSizeLayout.addView(new StageOptionBody(context, "Team Size", InputType.TYPE_CLASS_TEXT));
    }

    //Sets and builds the Tournament Color Picker field
    public void setTournamentColorPicker() {
        tournamentColorPickerLayout = (LinearLayout) findViewById(R.id.tournamentColorPicker_create_tournament);
        StageOptionColorPicker colorPicker = new StageOptionColorPicker(context);
        colorPicker.colorButton.setOnClickListener(v -> {
            openColorPicker();
        });

        tournamentColorPickerLayout.addView(colorPicker);
    }

    //Opens a color picker popup window
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

    //Sets the global color to the selected color
    private void setGlobalColor(int color) {
        this.color = color;
    }

    //Sets and builds the Stages header
    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne_create_tournament);
        firstHeaderLayout.addView(new DefaultTitle("Stages", context));
    }

    //Sets up and builds the Stage options component
    public void setStageOption() {
        stageOptionLayout = (LinearLayout) findViewById(R.id.stageOptions_create_tournament);
        StageOption stage = new StageOption(context, () -> updateParticipants());
        stage.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stage.setOptions(context);
                updateParticipants();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        stageOptionLayout.addView(stage);
        updateParticipants();
    }

    /**
     * Based on the information from the first stage, it controls and sets the participants in the
     * later added stages as well.
     */
    private void updateParticipants() {
        int nextParticipants = 0;
        boolean errored = false;
        participantsHasError = false;

        for (int i = 0; i < stageOptionLayout.getChildCount(); i++) {
            StageOption stage = (StageOption) stageOptionLayout.getChildAt(i);

            if (i == 0) {
                stage.setOpenNoOfParticipants();
            } else if (errored) {
                stage.setErrorNoOfParticipants();
            } else {
                stage.setForcedNoOfParticipants(nextParticipants);
            }

            try {
                nextParticipants = stage.getNumberOfNextParticipants();
                errored = false;
            } catch (NumberFormatException e) {
                errored = true;
                participantsHasError = true;
            }
        }

        if (errored) {
            numberOfWinners.setText("Number of winners has an error");
        } else {
            numberOfWinners.setText("Number of winners is " + nextParticipants);
        }

        System.out.println("Participants: " + nextParticipants + " " + participantsHasError);
    }

    //Adds a new stage option component
    public void addStageOption() {
        StageOption stage = new StageOption(context, () -> updateParticipants());
        stage.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stage.setOptions(context);
                updateParticipants();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        stageOptionLayout.addView(stage);
        updateParticipants();
    }

    //Sets and builds the Location Header
    public void setSecondHeader() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.headerTwo_create_tournament);
        secondHeaderLayout.addView(new DefaultTitle("Location", context));
    }

    //Sets and builds the Add Stage Option button
    public void setAddButton() {
        addButtonLayout = (LinearLayout) findViewById(R.id.addStageOptionButton_create_tournament);
        DefaultButton button = new DefaultButton(context, "Add");
        addButtonLayout.addView(button, 0);

        button.button.setOnClickListener(v -> {
            addStageOption();
        });
    }

    //Sets and builds the Set Location Button
    public void setLocation() {
        locationLayout = findViewById(R.id.location_create_tournament);
        DefaultButton locationEntry = new DefaultButton(context, "Pick Location");
        locationLayout.addView(locationEntry);
        locationEntry.button.setOnClickListener(v -> {
            startActivity(new Intent(CreateTournamentActivity.this,
                    MapSelectActivity.class));
        });
    }

    //Gets the selected location on resume from the shared preferences
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("LocationInfo", 0);
        this.location = sp.getString("location", "no location");
        Log.wtf("Gotten Location", this.location);
        setLocationButton(sp.getString("location", "no location"));
    }

    //Updates the text of the Set Location Button
    public void setLocationButton(String text) {
        if (!text.equals("no location")) {
            ((DefaultButton) locationLayout.getChildAt(0)).button.setText("Loc. Picked");
        } else {
            ((DefaultButton) locationLayout.getChildAt(0)).button.setText("Pick Location.");
        }
    }

    //Calls intent to open the phone's gallery
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
}
