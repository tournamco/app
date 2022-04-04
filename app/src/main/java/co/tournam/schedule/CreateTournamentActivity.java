package co.tournam.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

import co.tournam.api.ApiErrors;
import co.tournam.api.TournamentHandler;
import co.tournam.models.FakeData;
import co.tournam.models.TournamentModel;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.button.DefaultButtonIMG;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.stageoptions.StageOption;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.tournament_summary.TournamentSummaryListItem;

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
    private FakeData data;
    
    private boolean isOnline;
    
    private String name;
    private int color;
    private String game;
    private int teamSize;
    private boolean isPublic;
    private int gameLength;
    private List<TournamentModel.CreateStageModel> stages;
    private String location;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        data = new FakeData();
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

        
        
    }

    public void setBackButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        backButtonLayout = (LinearLayout) findViewById(R.id.backButton);
        backButtonLayout.addView( new DefaultButtonIMG(
                context,
                "",
                qr_icon
        ));

    }

    public void setCreateButton() {

        createButtonLayout = (LinearLayout) findViewById(R.id.backButton);
        createButtonLayout.addView(new DefaultButtonFilled(
                context,
                "Create"
        ));

        createButtonLayout.getChildAt(0).setOnClickListener(v -> {
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

//        this.name = tournamentNameLayout.getChildAt(0).getEntry().getText();
//
//        this.color = tournamentColorPickerLayout.getChildAt(0).getEntry().getText();
//
//        this.game = tournamentGameNameLayout.getChildAt(0).getEntry().getText();
//
//        this.teamSize = (int) tournamentTeamSizeLayout.getChildAt(0).getEntry().getText();
//
//        this.gameLength = (int) tournamentGameLengthLayout.getChildAt(0).getEntry().getText();

//        this.location;

//        for (int i = 0; i < stageOptionLayout.getChildCount(); i++) {
//            this.stages.add(stageOptionLayout.getChildAt(i).createStageFromEntry());
//        }


    }


    public void setTournamentBanner() {
        //TODO TournamentHandler.fromJSON
        tournamentBannerLayout = (LinearLayout) findViewById(R.id.tournamentBanner);
        tournamentBannerLayout.addView( new TournamentSummaryListItem(
                context,
                data.tournament));
    }

    public void setTournamentName() {

        tournamentNameLayout = (LinearLayout) findViewById(R.id.tournamentName);
        tournamentNameLayout.addView( new StageOptionBody(
                context,
                "Name"));
    }

    public void setTournamentGameName() {

        tournamentGameNameLayout = (LinearLayout) findViewById(R.id.tournamentGameName);
        tournamentGameNameLayout.addView( new StageOptionBody(
                context,
                "Game Name"));
    }

    public void setTournamentTeamSize() {

        tournamentTeamSizeLayout = (LinearLayout) findViewById(R.id.tournamentTeamSize);
        tournamentTeamSizeLayout.addView( new StageOptionBody(
                context,
                "Team Size"));
    }

    public void setTournamentColorPicker() {

        tournamentColorPickerLayout = (LinearLayout) findViewById(R.id.tournamentColorPicker);
        tournamentColorPickerLayout.addView( new StageOptionBody(
                context,
                "Color"));
    }

    public void setFirstHeader() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.headerOne);
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.STAGES
        ));
    }

    public void setStageOption() {

        stageOptionLayout = (LinearLayout) findViewById(R.id.teamlist);
        stageOptionLayout.addView( new StageOption(
                context
        ));

    }

    public void addStageOption() {

        stageOptionLayout.addView( new StageOption(
                context
        ));

    }

    public void setAddButton() {

        Drawable qr_icon = AppCompatResources.getDrawable(context, R.drawable.qr_icon);
        addButtonLayout = (LinearLayout) findViewById(R.id.addStageOptionButton);
        addButtonLayout.addView( new DefaultButtonIMG(
                context,
                "",
                qr_icon
        ));

        addButtonLayout.getChildAt(0).setOnClickListener(v -> {
            addStageOption();
        });

    }


}
