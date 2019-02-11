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

        Username = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Username.getText().toString().equals("admin") && Password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this, Dashboard2.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
