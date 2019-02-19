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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity {

    private EditText Name, Email, Password;
    private Button Register;
    private RadioGroup rg;
    private RadioButton male, female;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String gender;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    Map<String, Object> userMap = new HashMap<>();
    TextView weight;


    public void onClick(View v) {
        userMap.put("email",Name.getText().toString());
        userMap.put("name",Email.getText().toString());
        userMap.put("weight",weight.getText().toString());




        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Register", "createUserWithEmail:success");
                            user=mAuth.getCurrentUser();
                            db.collection("users").document(user.getUid())
                                    .set(userMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("doc", "success");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("doc", "fail",e);
                                }
                            });

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
        weight=findViewById(R.id.weight);


        Name = (EditText) findViewById(R.id.etName);
        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Register = (Button) findViewById(R.id.btnRegister);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

    }
}


