package co.tournam.ui.button;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class DefaultButtonFilled extends AbstractButton{

    private String title;
    public Button button;

    public DefaultButtonFilled(Context context, String title) {
        super(context);

        this.title = title;

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_button_gradient_fill, this, true);

        button = findViewById(R.id.default_button);
        button.setText(title);
    }
}
