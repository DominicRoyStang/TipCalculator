package com.uottawa.tipcalculator;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mainmenu_layout);

        // get setting button and assign its onClick()
        ImageButton settingsButton = (ImageButton) findViewById(R.id.SettingsImageButton);
        settingsButton.setOnClickListener(this); // calling onClick() method

        EditText billEditText = (EditText) findViewById(R.id.BillEditText);
        billEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //if (yourEditText.getText().toString().startsWith("$")) return;
                //billEditText.setText("$" + billEditText.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        /*
        //Get the widgets reference from XML layout
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
        //Set the default value of NumberPicker
        np.setValue(0);
        */
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.SettingsImageButton:
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            case R.id.CalculateButton:
                Toast.makeText(getApplicationContext(), "You pressed the calculate button", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name, menu);
        return true;
    }*/


}