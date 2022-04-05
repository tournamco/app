package co.tournam.ui.table;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TableLayout;

import co.tournam.schedule.R;

public class Table extends TableLayout {

    private int rowCount;



    public Table(Context context, int rowCount) {
        super(context);

        this.rowCount = rowCount;

        build(context);
    }

    private void build(Context context) {
        setOrientation(TableLayout.VERTICAL);
        setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(context, 16), dpToPx(context, 2), dpToPx(context, 16), dpToPx(context, 2));
        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table, this, true);

        for (int i = 0; i < this.rowCount; i++) {
            TableRow row = new TableRow(context);
            this.addView(row);
        }

    }



    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
