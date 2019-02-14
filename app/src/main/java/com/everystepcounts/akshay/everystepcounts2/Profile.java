package com.everystepcounts.akshay.everystepcounts2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private TextView name, email;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseUser user = mauth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        if (user != null)
        {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }
    }
}
