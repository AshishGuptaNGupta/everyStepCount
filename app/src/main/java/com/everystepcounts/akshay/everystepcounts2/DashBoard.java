package com.everystepcounts.akshay.everystepcounts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class DashBoard extends AppCompatActivity {

    Intent intent;

    String walking="0";
    String running="1";
    String activity;
    public void selectActivity(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.walking:
                if (checked)
                    activity=walking;
                    break;
            case R.id.Running:
                if (checked)
                    activity=running;
                    break;
        }
    }

    public void startActivity(View view){
        intent.putExtra("Activity",activity);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        intent=new Intent(this, MapsActivity.class);
    }
}
