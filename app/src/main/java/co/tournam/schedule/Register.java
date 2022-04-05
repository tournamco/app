package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.tournam.api.ApiErrors;
import co.tournam.api.UserHandler;

public class Register extends AppCompatActivity {

    public final int PASSWORD_LENGTH = 5;

    EditText userName, email, password, gamerTag, repeatPassword;
    Button registerButton, loginButton;

    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editPassword);
        repeatPassword = findViewById(R.id.editRepeatPassword);
        gamerTag = findViewById(R.id.editTag);
        registerButton = findViewById(R.id.SignUp);
        loginButton = findViewById(R.id.LogIn);


        progressBar = findViewById(R.id.progressBar);




        registerButton.setOnClickListener(view -> {
            String sEmail = email.getText().toString().trim();
            String sPassword = password.getText().toString().trim();
            String sRepeatPassword = repeatPassword.getText().toString().trim();

            if (TextUtils.isEmpty(sEmail)) {
                email.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(sPassword)) {
                password.setError("Password is required.");
                return;
            }

            if (TextUtils.isEmpty(sRepeatPassword)) {
                repeatPassword.setError("Password must be verified.");
            }

            if (sPassword.length() < PASSWORD_LENGTH) { //settable password length
                password.setError("Password must have consist of at least " + PASSWORD_LENGTH + " characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //register user
            UserHandler.create(userName.getText().toString().trim(), sPassword, gamerTag.getText().toString().trim(), sEmail, new UserHandler.CreateCompleted() {
                @Override
                public void success(String userId) {
                    Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void failure(ApiErrors error, String message) {
                    Toast.makeText(Register.this, "Not able to Create an User", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    System.err.println("API_ERROR: " + error.name() + " - " + message);
                }
            });

        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}