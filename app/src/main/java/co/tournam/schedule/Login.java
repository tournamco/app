package co.tournam.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


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

                auth.signInWithEmailAndPassword(sUserName, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{
                            Toast.makeText(Login.this, "Not able to Authenticate the User:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
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