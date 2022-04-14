package co.tournam.ui.table;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import co.tournam.schedule.R;

public class Table extends LinearLayout {

    private int rowCount;

    public Table(Context context, int rowCount) {
        super(context);

        this.rowCount = rowCount;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    private void buildContents(Context context) {
        for (int i = 0; i < this.rowCount; i++) {
            TableRow row = new TableRow(context, i%2==0);
            this.addView(row);
        }
    }
}
