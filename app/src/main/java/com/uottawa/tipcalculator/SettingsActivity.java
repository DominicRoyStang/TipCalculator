package com.uottawa.tipcalculator;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.CalculateButton:
                Toast.makeText(getApplicationContext(), "You pressed the calculate button", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
