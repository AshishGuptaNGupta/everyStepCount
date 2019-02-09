package com.everystepcounts.akshay.everystepcounts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private EditText Username, Password;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(Username.getText().toString(), "admin")&&Objects.equals(Password.getText().toString(),"admin"))
                {
                    Toast.makeText(MainActivity.this,"You have Authenticated Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this, Dashboard.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
