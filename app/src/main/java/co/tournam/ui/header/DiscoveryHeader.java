package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class DiscoveryHeader extends BigHeader {
    private TextView title;
    public Button button;

    public DiscoveryHeader(Context context) {
        super(context, "Discovery");

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_discovery_header, this, true);

        title = (TextView) findViewById(R.id.title);
        title.setText(getName());

        button = (Button) findViewById(R.id.button_create);
    }

    public View getButton() {
        return button;
    }
}
