package com.everystepcounts.akshay.everystepcounts2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText username, password;
    private Button login, forgotPassword;
    FirebaseUser user;

    public void onClick(View view) {
        mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Login", "Logged in");
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), Dashboard2.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"Incorrect credentials",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(View view){
        Intent register=new Intent(this,Register_Page.class);
        startActivity(register);
    }
    public void forgotPassword(View view){
        Intent register=new Intent(this,Forgot_Password.class);
        startActivity(register);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = mAuth.getCurrentUser();

        username = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        forgotPassword = (Button) findViewById(R.id.btnForgotpass);

    }
}