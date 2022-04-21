package co.tournam.ui.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class SmallHeader extends AbstractHeader {
    private TextView titleText;
    private TextView subtitleText;
    private ImageButton backButton;
    private View extraButton;
    private String subtitle;
    private FinishListener finished;

    /**
     * The constructor for SmallHeader including the subtitle.
     *
     * @param context  the current context.
     * @param name     the input name.
     * @param subtitle the input subtitle.
     * @param finished the input listener.
     */
    public SmallHeader(Context context, String name, String subtitle, FinishListener finished) {
        this(context, name, subtitle, finished, null);
    }

    /**
     * The constructor for smallHeader excluding the subtitle and extrabutton.
     *
     * @param context  the current context.
     * @param name     the input name.
     * @param finished the input listener.
     */
    public SmallHeader(Context context, String name, FinishListener finished) {
        this(context, name, null, finished, null);
    }

    /**
     * The constructor for smallheader excluding the subtitle.
     *
     * @param context     the current context.
     * @param name        the input name.
     * @param finished    the input listener.
     * @param extraButton the extra button view.
     */
    public SmallHeader(Context context, String name, FinishListener finished, View extraButton) {
        this(context, name, null, finished, extraButton);
    }

    /**
     * The full constructor for smallHeader.
     *
     * @param context     the current context.
     * @param name        the input name.
     * @param subtitle    the input subtitle.
     * @param finished    the input listener.
     * @param extraButton the extra button view.
     */
    public SmallHeader(Context context, String name, String subtitle, FinishListener finished, View extraButton) {
        super(context, name);

        this.subtitle = subtitle;
        this.finished = finished;
        this.extraButton = extraButton;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.small_header, this, true);

        titleText = (TextView) findViewById(R.id.header_title);
        titleText.setText(getName());

        subtitleText = (TextView) findViewById(R.id.header_subtitle);

        if (subtitle != null) {
            subtitleText.setText(subtitle);
        } else {
            subtitleText.setVisibility(GONE);
        }

        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            finished.onFinish();
        });

        LinearLayout buttonContainer = findViewById(R.id.button_container);
        if (extraButton != null) {
            buttonContainer.addView(extraButton);
        } else {
            buttonContainer.setVisibility(GONE);
        }
    }

    /**
     * the interface for the FinishListener.
     */
    public interface FinishListener {
        void onFinish();
    }
}
