package com.uottawa.tipcalculator;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mainmenu_layout);

        // Find our "Layout Buttons"
        RelativeLayout tipLayoutButton = (RelativeLayout) findViewById(R.id.TipRelativeLayout);

        // Create background
        final int[] attrs = new int[]{R.attr.selectableItemBackground};
        final TypedArray typedArray = this.obtainStyledAttributes(attrs);
        final int backgroundResource = typedArray.getResourceId(0, 0);

        // Apply background to our "layout buttons" (for when they are pressed)
        tipLayoutButton.setBackgroundResource(backgroundResource);
        typedArray.recycle();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.TipRelativeLayout:
                Toast.makeText(getApplicationContext(), "TipRelativeLayout pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.PayersRelativeLayout:
                Toast.makeText(getApplicationContext(), "PayersRelativeLayout pressed", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "You pressed the About button", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}