package com.everystepcounts.akshay.everystepcounts2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    private TextView name, email;
    private ImageView Edit;
    String TAG = "Log";
    private EditText Age, Weight, Mobile, Gender, Dob;
    private int clickcount = 0;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseUser user = mauth.getCurrentUser();
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        Age = (EditText) findViewById(R.id.etage);
        Weight = (EditText) findViewById(R.id.etWeight);
        Mobile = (EditText) findViewById(R.id.etMobile);
        Gender = (EditText) findViewById(R.id.etGender);
        Dob= (EditText) findViewById(R.id.etDob);

        Edit = (ImageView) findViewById(R.id.edit);

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickcount == 0) {
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    Age.setEnabled(false);
                    Weight.setEnabled(false);
                    Mobile.setEnabled(false);
                    Gender.setEnabled(false);
                    Dob.setEnabled(false);
                    clickcount = 1;
                }
        else
                    {
                        Toast.makeText(getApplicationContext(), "Enable Editing", Toast.LENGTH_SHORT).show();
                        Age.setEnabled(true);
                        Weight.setEnabled(true);
                        Mobile.setEnabled(true);
                        Gender.setEnabled(true);
                        Dob.setEnabled(true);
                        clickcount = 0;
                    }

            }
        });

        if (user != null)
        {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());

        }

    }
}
