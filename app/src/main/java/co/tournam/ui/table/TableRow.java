package co.tournam.ui.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class TableRow extends LinearLayout {

    private TextView rowTitle;
    private TextView rowData;
    private boolean even;

    public TableRow(Context context, boolean even) {
        super(context);

        this.even = even;

        build(context);
    }

    public void build(Context context) {
        setOrientation(HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table_row, this, true);

        if(!even) {
            setBackgroundColor(getResources().getColor(R.color.gray_400));
        }

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
