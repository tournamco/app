package co.tournam.ui.table;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;

import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class Table extends TableLayout {

    private UserModel userModel;



    private TextView dateJoinedText;
    private TextView winRatioText;
    private TextView matchWinsText;
    private TextView tournamentWinRatioText;
    private TextView tournamentWinsText;

    private String dateJoin;
    private String winPercentage;
    private String tWinPercentage;
    private int wins;
    private int tWins;




    public Table(Context context, UserModel userModel, String winPercentage, String dateJoin,
                 String tWinPercentage, int wins, int tWins) {
        super(context);
        this.dateJoin = dateJoin;
        this.winPercentage = winPercentage;
        this.tWins = tWins;
        this.tWinPercentage = tWinPercentage;
        this.userModel = userModel;


        build(context);
    }

    private void build(Context context) {
        setOrientation(TableLayout.HORIZONTAL);
        setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(context, 16), dpToPx(context, 2), dpToPx(context, 16), dpToPx(context, 2));
        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table, this, true);


        dateJoinedText = (TextView) findViewById(R.id.dateJoin);
        winRatioText = (TextView) findViewById(R.id.winRatio);
        matchWinsText= (TextView) findViewById(R.id.matchWins);
        tournamentWinRatioText = (TextView) findViewById(R.id.tournamentWinRatio);
        tournamentWinsText = (TextView) findViewById(R.id.tournamentWins);

        //setDate(userModel.getDateJoined());
        //setWinRatio(userModel.getWinRatio());
        //setMatchWins(userModel.getMatchWins());
        //setTournamentWinRatio(userModel.getTournamentWinRatio());
       // setTournamentWins(userModel.getTournamentWins());


    }

    private void setDate(String dateJoin){ dateJoinedText.setText(dateJoin);}

    private void setWinRatio(String winPercentage){winRatioText.setText(winPercentage);}

    private void setMatchWins(int wins){matchWinsText.setText(Integer.toString(wins));}

    private void setTournamentWinRatio(String tWinPercentage){tournamentWinRatioText.setText(tWinPercentage);}

    private void setTournamentWins(int tWins){tournamentWinsText.setText(Integer.toString(tWins));}


    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
