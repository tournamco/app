package co.tournam.ui.stageoptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import co.tournam.api.ApiErrors;
import co.tournam.api.UserHandler;
import co.tournam.schedule.R;
import co.tournam.ui.button.DefaultButtonFilled;

public class ChangePasswordObject extends LinearLayout {

    EditText oldPass;
    EditText newPass;
    EditText confirmNewPass;
    DefaultButtonFilled change;
    public LinearLayout changePassLayout;

    /**
     * The constructor for ChangePasswordObject
     *
     * @param context the current context
     */
    public ChangePasswordObject(Context context) {
        super(context);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.change_password_object, this, true);

        oldPass = (EditText) findViewById(R.id.change_pass_old);
        newPass = (EditText) findViewById(R.id.change_pass_new);
        confirmNewPass = (EditText) findViewById(R.id.change_pass_newconf);


        change = new DefaultButtonFilled(context, "Change");
        changePassLayout = findViewById(R.id.change_pass_button);
        changePassLayout.addView(change);

        change.button.setOnClickListener(v -> {
            String oldPassword = oldPass.getText().toString();
            String newPassword = newPass.getText().toString();
            String confirmal = confirmNewPass.getText().toString();

            if (newPassword.equals(confirmal)) {
                changePassword(context, oldPassword, newPassword);
            } else {
                Toast.makeText(context, "The passwords don't match", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * A function that attempts to change the password.
     *
     * @param context the current context
     * @param oldPass the old password
     * @param newPass the new password.
     */
    public void changePassword(Context context, String oldPass, String newPass) {
        UserHandler.changePassword(oldPass, newPass, () ->
                Toast.makeText(context, "Password Changed!", Toast.LENGTH_SHORT).show());
    }


}
