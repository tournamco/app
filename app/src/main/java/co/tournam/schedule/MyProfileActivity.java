package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.PersistentCookieStore;
import co.tournam.api.UploadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.models.UserModel;
import co.tournam.ui.button.DefaultButton;
import co.tournam.ui.header.SmallHeader;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.ChangePasswordObject;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.table.Table;
import co.tournam.ui.table.TableRow;
import co.tournam.ui.title.DefaultTitle;

public class MyProfileActivity extends AppCompatActivity {

    //Variable Declarations
    private LinearLayout userIconLayout;
    private LinearLayout usernameLayout;
    private LinearLayout gamerTagLayout;
    private LinearLayout emailLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout changePasswordLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout statisticsLayout;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Context context;
    private UserModel user;
    private Bitmap newUserIcon;
    private String newUserIconID;


    //On create method of the My Profile Activity calling and setting up functions and variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        this.context = this.getApplicationContext();

        // Open Gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            newUserIcon = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            new UploadImageWorker(imageId -> {
                                this.newUserIconID = imageId;
                                changeIcon();
                            }).execute(newUserIcon);
                            ((ImageListItem) userIconLayout.getChildAt(0)).setImage(newUserIcon);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                });

        getUserInfo();

        LinearLayout header = findViewById(R.id.header);
        DefaultButton button = new DefaultButton(context, "Logout");
        button.button.setOnClickListener(v -> {
            // TODO: Logout
            UserHandler.logout(() -> {
                Intent intent = new Intent(context, LoginActivity.class);
                getApplicationContext().getSharedPreferences(PersistentCookieStore.COOKIE_PREFS, 0).edit().clear().apply();
                startActivity(intent);
            });
        });
        header.addView(new SmallHeader(context, "My Profile", () -> this.finish(), button));
    }

    //Gets the information of the user from the server and using that information sets up the page
    public void getUserInfo() {
        UserHandler.me(me -> {
            setUserInfo(me);
            setUserIconLayout();
            setUsernameLayout();
            setGamerTagLayout();
            setEmailLayout();
            setFirstHeaderLayout();
            setChangePasswordLayout();
            setSecondHeaderLayout();
            setStatisticsLayout();
        });
    }

    //Sets the global user model equal to the passed in one
    public void setUserInfo(UserModel me) {
        this.user = me;
    }

    //Gets and sets the users icon
    public void setUserIconLayout() {

        userIconLayout = (LinearLayout) findViewById(R.id.userIcon_mypf);

        ImageListItem image = new ImageListItem(context, null);
        userIconLayout.addView(image);

        try {
            new DownloadImageWorker(bitmap -> image.setImage(bitmap)).execute(user.getIcon());
        } catch (NullPointerException e) {
            Log.w("User Icon is null", e);
        }


        ImageButton change = (ImageButton) findViewById(R.id.change_userIcon);
        change.setOnClickListener(v -> {
            openGallery();

        });
    }

    //Changes the icon of the user to the value of newUserIconID
    public void changeIcon() {
        UserHandler.changeIcon(this.newUserIconID, () ->
                Toast.makeText(MyProfileActivity.this, "Icon changed.", Toast.LENGTH_SHORT).show());
    }

    //Opens an intent of the gallery
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    //Sets and builds the username field
    public void setUsernameLayout() {

        usernameLayout = (LinearLayout) findViewById(R.id.username_mypf);
        StageOptionBody body = new StageOptionBody(context, "Username", InputType.TYPE_CLASS_TEXT);
        body.setEntryText(user.getUsername());
        usernameLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_username);
        change.setOnClickListener(v -> {

            UserHandler.changeUsername(((StageOptionBody) usernameLayout.getChildAt(0)).getEntry(),
                    () -> Toast.makeText(MyProfileActivity.this, "Username changed.", Toast.LENGTH_SHORT).show());
        });
    }

    //Sets and builds the gamertag field
    public void setGamerTagLayout() {
        gamerTagLayout = (LinearLayout) findViewById(R.id.gamerTag_mypf);
        StageOptionBody body = new StageOptionBody(context, "Gamertag", InputType.TYPE_CLASS_TEXT);
        body.setEntryText(user.getGamerTag());
        gamerTagLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_gamerTag);
        change.setOnClickListener(v -> {

            UserHandler.changeGamertag(((StageOptionBody) gamerTagLayout.getChildAt(0)).getEntry(),
                    () -> Toast.makeText(MyProfileActivity.this, "Gamertag changed.", Toast.LENGTH_SHORT).show());
        });
    }

    //Sets and builds the email field
    public void setEmailLayout() {
        emailLayout = (LinearLayout) findViewById(R.id.email_mypf);
        StageOptionBody body = new StageOptionBody(context, "Email", InputType.TYPE_CLASS_TEXT);
        body.setEntryText(user.getEmail());
        emailLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_email);
        change.setOnClickListener(v -> {

            UserHandler.changeEmail(((StageOptionBody) emailLayout.getChildAt(0)).getEntry(),
                    () -> Toast.makeText(MyProfileActivity.this, "Email changed.", Toast.LENGTH_SHORT).show());
        });
    }

    //Sets and builds the Change Password Header
    public void setFirstHeaderLayout() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.first_header_mypf);
        firstHeaderLayout.addView(new DefaultTitle("Change Password", context));
    }

    //Sets and builds the change username field
    public void setChangePasswordLayout() {

        changePasswordLayout = (LinearLayout) findViewById(R.id.change_password_mypf);

        ChangePasswordObject pass = new ChangePasswordObject(context);
        changePasswordLayout.addView(pass);

    }

    //Sets and builds the Statistics Header
    public void setSecondHeaderLayout() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.second_header_mypf);
        secondHeaderLayout.addView(new DefaultTitle("Statistics", context));
    }

    //Sets and builds the statistics field
    public void setStatisticsLayout() {
        statisticsLayout = (LinearLayout) findViewById(R.id.statistics_table_mypf);
        statisticsLayout.addView(setUpTable(context, user));

    }

    //Generates the statistics table
    public Table setUpTable(Context context, UserModel me) {
        //TODO Add statistics
        Table mystats = new Table(this.context, 5);
        setTableRow(mystats, 0, "Date Joined", "");
        setTableRow(mystats, 1, "Match win ratio", "");
        setTableRow(mystats, 2, "Match wins", "");
        setTableRow(mystats, 3, "Tournament win ratio", "");
        setTableRow(mystats, 4, "Tournament wins", "");

        return mystats;
    }

    //Adds a row to the statistics table
    public void setTableRow(Table table, int index, String title, String data) {

        ((TableRow) table.getChildAt(index)).setTitleText(title);
        ((TableRow) table.getChildAt(index)).setDataText(data);
    }


}
