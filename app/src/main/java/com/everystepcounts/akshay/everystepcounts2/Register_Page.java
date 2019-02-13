package com.everystepcounts.akshay.everystepcounts2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Page extends AppCompatActivity {

    private EditText Name, Email, Password;
    private Button Register;
    private RadioGroup rg;
    private RadioButton rMale, rFemale;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void onClick(View v) {
        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.i("Register", "createUserWithEmail:success");

                        } else {

                            Log.i( "register", "createUserWithEmail:fail");
                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Name = (EditText) findViewById(R.id.etName);
        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Register = (Button) findViewById(R.id.btnRegister);
        rMale = (RadioButton) findViewById(R.id.rbtnMale);
        rFemale = (RadioButton) findViewById(R.id.rbtnFemale);

    }
}


