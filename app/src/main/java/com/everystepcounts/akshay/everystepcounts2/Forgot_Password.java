package com.everystepcounts.akshay.everystepcounts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Forgot_Password extends AppCompatActivity {
    private Button ResetPassword;
    private EditText Email;
    private FirebaseAuth mauth = FirebaseAuth.getInstance();

    public void onClick(View view)
    {
        String emailAddress = Email.getText().toString();
        mauth.sendPasswordResetEmail(emailAddress);
        Toast.makeText(getApplicationContext(),"Email has been Sent",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        Email = (EditText)findViewById(R.id.etEmailid);
        ResetPassword = (Button)findViewById(R.id.btnResetpass);
    }
}
