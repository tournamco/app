package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import co.tournam.api.ApiErrors;
import co.tournam.api.UserHandler;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button loginButton, registerButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.LogIn);
        registerButton = findViewById(R.id.SignUp);

        auth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sUserName = userName.getText().toString().trim();
                String sPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(sUserName)){
                    userName.setError("Email is required.");
                    return ;
                }

                if(TextUtils.isEmpty(sPassword)){
                    password.setError("Password is required.");
                    return;
                }

                //authenticate the user

                UserHandler.login(sUserName, sPassword, new UserHandler.LoginCompleted() {
                    @Override
                    public void success(String userId) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void failure(ApiErrors error, String message) {
                        Toast.makeText(LoginActivity.this, "Not able to Authenticate the User:" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}