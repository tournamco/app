package co.tournam.ui.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class TableRow extends TableLayout {

    private TextView rowTitle;
    private TextView rowData;

    public TableRow(Context context) {

        super(context);


        build(context);
    }

    public void build(Context context) {

        setOrientation(HORIZONTAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table_row, this, true);

        rowTitle = (TextView) findViewById(R.id.row_title);
        rowData = (TextView) findViewById(R.id.row_data);
        setTitleText("Empty");
        setDataText("Empty");
    }

    public void setTitleText(String title) {
        rowTitle.setText(title);
    }

    public void setDataText(String data) {
        rowData.setText(data);
    }
}
