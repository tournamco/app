package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText userName, email, password, gamerTag, repeatPassword;
    Button registerButton, loginButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


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

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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

                if (sPassword.length() < 5) { //settable password length
                    password.setError("Password must have appropriate number of characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register user
                mAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Not able to Create an User: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}