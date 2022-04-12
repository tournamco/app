package co.tournam.schedule;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.ApiErrors;
import co.tournam.api.DownloadImageWorker;
import co.tournam.api.UserHandler;
import co.tournam.models.UserModel;
import co.tournam.ui.header.header;
import co.tournam.ui.header.headerTitle;
import co.tournam.ui.imagelist.ImageListItem;
import co.tournam.ui.stageoptions.ChangePasswordObject;
import co.tournam.ui.stageoptions.StageOptionBody;
import co.tournam.ui.table.Table;
import co.tournam.ui.table.TableRow;

public class MyProfileActivity extends AppCompatActivity {

    private LinearLayout userIconLayout;
    private LinearLayout usernameLayout;
    private LinearLayout gamerTagLayout;
    private LinearLayout emailLayout;
    private LinearLayout firstHeaderLayout;
    private LinearLayout changePasswordLayout;
    private LinearLayout secondHeaderLayout;
    private LinearLayout statisticsLayout;

    private Context context;
    private UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        this.context = this.getApplicationContext();


        getUserInfo();
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
        });
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
        firstHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.CHANGE_PASSWORD
        ));
    }

    public void setChangePasswordLayout() {

        changePasswordLayout = (LinearLayout) findViewById(R.id.change_password_mypf);

        ChangePasswordObject pass = new ChangePasswordObject(context);
        changePasswordLayout.addView(pass);

    }

    public void setSecondHeaderLayout() {
        secondHeaderLayout = (LinearLayout) findViewById(R.id.second_header_mypf);
        secondHeaderLayout.addView( new header(
                context,
                null,
                headerTitle.STATISTICS
        ));
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
