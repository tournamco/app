package co.tournam.schedule;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.ui.Slider.Slider;
import co.tournam.ui.button.DefaultButtonFilled;
import co.tournam.ui.filterdropdown.FilterDropdown;
import co.tournam.ui.header.headerTournament;
import co.tournam.ui.tournament_summary.TournamentSummaryList;

public class TournamentActivity extends LinearLayout {

    private FilterDropdown filter;
    private Slider slider;
    private DefaultButtonFilled button;

    public TournamentActivity(Context context) {

        super(context);

        build(context);
    }

    public void build(Context context) {

        setOrientation(VERTICAL);


        buildContents(context);
    }

    public void buildContents(Context context) {

//        LinearLayout rootLayout = new LinearLayout(context);
//
//        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT,
//                2.0f
//        );

        //rootLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,3));


        //rootLayout.setOrientation(LinearLayout.VERTICAL);


        // TODO: GET MATCHLIST FROM SERVER
        List<TournamentModel> fake = new ArrayList<TournamentModel>();


//        DefaultButtonFilled button = new DefaultButtonFilled(this.getContext(), "P");

//        button.setLayoutParams(param);

        headerTournament header = new headerTournament(this.getContext(), "Tournaments");

//        header.setLayoutParams(param);

        TournamentSummaryList list = new TournamentSummaryList(this.getContext(), fake);

//        list.setLayoutParams(param);

//        this.addView(button);
        this.addView(header);
        this.addView(list);






    }
}
