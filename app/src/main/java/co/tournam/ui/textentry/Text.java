package co.tournam.ui.textentry;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class Text extends LinearLayout {

    private TextView textView;
    private String text;

    public Text(Context context, String text) {
        super(context);

        this.text = text;

        build(context);
    }

    private void build(Context context) { buildContents(context);}

    private void buildContents(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_text, this, true);

        textView = (TextView)findViewById(R.id.defaultText);
        textView.setText(text);
    }
}
