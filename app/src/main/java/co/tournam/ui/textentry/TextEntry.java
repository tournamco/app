package co.tournam.ui.textentry;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.tournam.schedule.R;

public class TextEntry extends AbstractTextEntry {

    private TextView text;
    private EditText editableText;
    private String name;
    private boolean editable;


    public TextEntry(Context context, String name, boolean editable) {
        super(context);
        this.name = name;
        this.editable = editable;
        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    public void changeEditable(boolean editable) {
        this.editable = editable;
        funEditable(editable);
    }

    public void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textentry_with_title, this, true);

        text = (TextView) findViewById(R.id.textentry_title);
        editableText = (EditText) findViewById(R.id.editTextEntry);

        setName(name);
        funEditable(editable);
    }

    private void setName(String name) { text.setText(name);}
    private void funEditable(boolean editable) {editableText.setEnabled(editable);}

}
