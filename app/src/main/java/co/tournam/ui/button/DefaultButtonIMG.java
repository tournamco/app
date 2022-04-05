package co.tournam.ui.button;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import co.tournam.schedule.R;

public class DefaultButtonIMG extends AbstractButton {

    private String title;
    public Button button;
    private Drawable image;

    public DefaultButtonIMG(Context context, String title, Drawable image) {
        super(context);

        this.title = title;
        this.image = image;

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_ui_button_with_img, this, true);

        button = findViewById(R.id.default_button_with_img);
        button.setText(title);
        button.setCompoundDrawablesRelative(null, null, image, null);
        button.setCompoundDrawablesWithIntrinsicBounds(null,null, image,null);
    }
}