package co.tournam.ui.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class DefaultTitle extends AbstractTitle{
    private TextView titleText;

    public DefaultTitle(String name, Context context) {
        super(name, context);

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.default_title, this, true);

        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText(getName());
    }
}
