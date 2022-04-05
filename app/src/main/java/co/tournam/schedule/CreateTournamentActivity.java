package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.stageoptions.StageOption;
import co.tournam.ui.stageoptions.StageOptionBody;

public class CreateTournamentActivity extends AppCompatActivity {

    Context context;
    private LinearLayout tournamentBannerLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout tournamentNameLayout;
    private LinearLayout tournamentGameNameLayout;
    private LinearLayout tournamentTeamSizeLayout;
    private LinearLayout tournamentColorPickerLayout;
    private LinearLayout stageOptionLayout;
    private LinearLayout createButtonLayout;
    private LinearLayout addButtonLayout;
    private LinearLayout backButtonLayout;
    private LinearLayout locationLayout;
    
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




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        context = this.getApplicationContext();

        setBackButton();
        setCreateButton();
        setTournamentBanner();
        setTournamentName();
        setTournamentGameName();
        setTournamentTeamSize();
        setTournamentColorPicker();
        setFirstHeader();
        setStageOption();
        setAddButton();
        setLocation();

        onlineBox = findViewById(R.id.isOnline_checkbox_create_tournament);
        publicBox = findViewById(R.id.isPublic_checkbox_create_tournament);

        
    }

    public void setBackButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        backButtonLayout = (LinearLayout) findViewById(R.id.backButton_create_tournament);
        DefaultButton back = new DefaultButton(context, "Back");
        backButtonLayout.addView(back);
        back.button.setOnClickListener(v -> startActivity(new Intent(context, MainActivity.class)));


    }

    public void setCreateButton() {

        createButtonLayout = (LinearLayout) findViewById(R.id.createButton_create_tournament);
        createButtonLayout.addView(new DefaultButtonFilled(
                context,
                "Create"
        ));

        ((DefaultButtonFilled) createButtonLayout.getChildAt(0)).button.setOnClickListener(v -> {
            infoUpdate();
            createTournament();
        });


    }
    
    public void createTournament() {
        if (this.isOnline) {
            TournamentHandler.createOnline(this.name, this.color, this.game, this.teamSize, 
                    this.isPublic, this.gameLength, this.stages, new TournamentHandler.CreateComplete() {
                @Override
                public void success(String id) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        } else {
            TournamentHandler.createOffline(this.name, this.color, this.game, this.teamSize,
                    this.isPublic, this.gameLength, this.location, this.stages, new TournamentHandler.CreateComplete() {
                @Override
                public void success(String id) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });

        }
    }

    public void infoUpdate() {

        this.name = ((StageOptionBody) tournamentNameLayout.getChildAt(0)).getEntry();

        this.color = Integer.parseInt(((StageOptionBody) tournamentColorPickerLayout.getChildAt(0)).getEntry());

        this.game = ((StageOptionBody) tournamentGameNameLayout.getChildAt(0)).getEntry();

        this.teamSize = Integer.parseInt(((StageOptionBody) tournamentTeamSizeLayout.getChildAt(0)).getEntry());

        this.location = ((StageOptionBody) locationLayout.getChildAt(0)).getEntry();

        for (int i = 0; i < stageOptionLayout.getChildCount(); i++) {
            this.stages.add(((StageOption) stageOptionLayout.getChildAt(i)).createStageFromEntry());
        }

        //TODO Change once we understand what game length means
        this.gameLength = 60;

        this.isOnline = onlineBox.isChecked();
        this.isPublic = publicBox.isChecked();

    }


    public void setTournamentBanner() {

        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner_create_tournament);
//        tournamentBannerLayout.addView( new TournamentSummaryListItem(
//                context,
//                ));
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
        tournamentColorPickerLayout.addView( new StageOptionBody(
                context,
                "Color"));
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne_create_tournament);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.STAGES
        ));
    }

    public void setStageOption() {

        stageOptionLayout = (LinearLayout) findViewById(R.id.stageOptions_create_tournament);
        StageOption stage = new StageOption(context);
        stageOptionLayout.addView(stage);



    }

    public void addStageOption() {

        StageOption stage = new StageOption(context);
        stageOptionLayout.addView(stage);

        Log.e("hi", String.valueOf(stageOptionLayout.getChildCount()));


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


}
