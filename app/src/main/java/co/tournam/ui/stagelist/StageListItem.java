package co.tournam.ui.stagelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import co.tournam.models.stage.PoolsStageModel;
import co.tournam.schedule.R;

public class StageListItem extends LinearLayout {

    private PoolsStageModel stage;
    private Button button;

    public StageListItem(Context context, PoolsStageModel stage) {
        super(context);

        this.stage = stage;
        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stagelist_row_base, this, true);

        button = findViewById(R.id.stagelist_button);
        button.setText(stage.getName());
    }
}
