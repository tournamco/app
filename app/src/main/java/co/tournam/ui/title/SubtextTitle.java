package co.tournam.ui.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class SubtextTitle extends AbstractTitle {
    private TextView titleText;
    private String subtext;

    public SubtextTitle(String name, String subtext, Context context) {
        super(name, context);

        this.subtext = subtext;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subtext_title, this, true);

        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText(getName());

        TextView subtext = (TextView) findViewById(R.id.subtitle_text);
        subtext.setText(this.subtext);
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }
}
