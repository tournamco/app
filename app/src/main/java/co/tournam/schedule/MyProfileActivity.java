package co.tournam.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
                            new UploadImageWorker(imageId -> this.newUserIconID = imageId).execute(newUserIcon);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED)  {
                    }
                });

        getUserInfo();

        LinearLayout header = findViewById(R.id.header);
        DefaultButton button = new DefaultButton(context, "Logout");
        button.button.setOnClickListener(v -> {
            // TODO: Logout
        });
        header.addView(new SmallHeader(context, "My Profile", () -> this.finish(), button));
    }

    public void getUserInfo() {

        UserHandler.me(new UserHandler.MeCompleted() {
            @Override
            public void success(UserModel me) {
                setUserInfo(me);
                setUserIconLayout();
                setUsernameLayout();
                setGamerTagLayout();
                setEmailLayout();
                setFirstHeaderLayout();
                setChangePasswordLayout();
                setSecondHeaderLayout();
                setStatisticsLayout();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void setUserInfo(UserModel me) {
        this.user = me;
        Log.w("Username", user.getUsername());
        Log.w("UserID", user.getId());
        Log.w("User Gamertag", user.getGamerTag());
        Log.w("User Email", user.getEmail());
    }

    public void setUserIconLayout() {

        userIconLayout = (LinearLayout) findViewById(R.id.userIcon_mypf);

        ImageListItem image = new ImageListItem(context, null);
        userIconLayout.addView(image);

        try{
            new DownloadImageWorker(bitmap -> image.setImage(bitmap)).execute(user.getIcon());
        } catch (NullPointerException e) {
            Log.w("User Icon is null", e);
        }


        ImageButton change = (ImageButton) findViewById(R.id.change_userIcon);
        change.setOnClickListener(v -> {
            //TODO open gallery to change icon
            openGallery();
            changeIcon();
        });
    }

    public void changeIcon() {
        //Log.w("User Icon value:");

        UserHandler.changeIcon(this.newUserIconID, new UserHandler.ChangeComplete() {
            @Override
            public void success() {
                Toast.makeText(MyProfileActivity.this, "Icon changed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(ApiErrors error, String message) {
                System.err.println("API_ERROR: " + error.name() + " - " + message);
            }
        });
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    public void setUsernameLayout() {

        usernameLayout = (LinearLayout) findViewById(R.id.username_mypf);
        StageOptionBody body = new StageOptionBody(context, "Username");
        body.setEntryText(user.getUsername());
        usernameLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_username);
        change.setOnClickListener(v -> {

            UserHandler.changeUsername(((StageOptionBody) usernameLayout.getChildAt(0)).getEntry(),
                    new UserHandler.ChangeComplete() {
                @Override
                public void success() {
                    Toast.makeText(MyProfileActivity.this, "Username changed.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });
        });
    }

    public void setGamerTagLayout() {
        gamerTagLayout = (LinearLayout) findViewById(R.id.gamerTag_mypf);
        StageOptionBody body = new StageOptionBody(context, "Gamertag");
        body.setEntryText(user.getGamerTag());
        gamerTagLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_gamerTag);
        change.setOnClickListener(v -> {

            UserHandler.changeGamertag(((StageOptionBody) gamerTagLayout.getChildAt(0)).getEntry(),
                    new UserHandler.ChangeComplete() {
                        @Override
                        public void success() {
                            Toast.makeText(MyProfileActivity.this, "Gamertag changed.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(ApiErrors error, String message) {
                            System.err.println("API_ERROR: " + error.name() + " - " + message);
                        }
                    });
        });
    }

    public void setEmailLayout() {
        emailLayout = (LinearLayout) findViewById(R.id.email_mypf);
        StageOptionBody body = new StageOptionBody(context, "Email");
        body.setEntryText(user.getEmail());
        emailLayout.addView(body);

        ImageButton change = (ImageButton) findViewById(R.id.change_email);
        change.setOnClickListener(v -> {

            UserHandler.changeEmail(((StageOptionBody) emailLayout.getChildAt(0)).getEntry(),
                    new UserHandler.ChangeComplete() {
                        @Override
                        public void success() {
                            Toast.makeText(MyProfileActivity.this, "Email changed.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(ApiErrors error, String message) {
                            System.err.println("API_ERROR: " + error.name() + " - " + message);
                        }
                    });
        });
    }

    public void setFirstHeaderLayout() {
        firstHeaderLayout = (LinearLayout) findViewById(R.id.first_header_mypf);
        firstHeaderLayout.addView(new DefaultTitle("Change Password", context));
    }

    public void setChangePasswordLayout() {

        changePasswordLayout = (LinearLayout) findViewById(R.id.change_password_mypf);

        ChangePasswordObject pass = new ChangePasswordObject(context);
        changePasswordLayout.addView(pass);

    }

    public void setSecondHeaderLayout() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.second_header_mypf);
        secondHeaderLayout.addView(new DefaultTitle("Statistics", context));
    }

    public void setStatisticsLayout() {

        statisticsLayout = (LinearLayout) findViewById(R.id.statistics_table_mypf);
        statisticsLayout.addView(setUpTable(context, user));

    }

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

    public void setTableRow(Table table, int index, String title, String data) {

        ((TableRow) table.getChildAt(index)).setTitleText(title);
        ((TableRow) table.getChildAt(index)).setDataText(data);
    }


}
