package co.tournam.ui.button;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

import co.tournam.schedule.R;

public class DefaultButton extends AbstractButton {

    private String title;
    public Button button;

    public DefaultButton(Context context, String title) {
        super(context);

        this.title = title;

        build(context);
    }

    public void build(Context context) {


        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_ui_button, this, true);

        button = findViewById(R.id.default_button);
        button.setText(title);
    }
}
