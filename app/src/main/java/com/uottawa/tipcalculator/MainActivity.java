package com.uottawa.tipcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
        RelativeLayout payersLayoutButton = (RelativeLayout) findViewById(R.id.PayersRelativeLayout);

        // Create background
        final int[] attrs = new int[]{R.attr.selectableItemBackground};
        final TypedArray typedArray = this.obtainStyledAttributes(attrs);
        final int backgroundResource = typedArray.getResourceId(0, 0);

        // Apply background to our "layout buttons" (for when they are pressed)
        tipLayoutButton.setBackgroundResource(backgroundResource);
        payersLayoutButton.setBackgroundResource(backgroundResource);
        typedArray.recycle();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.TipRelativeLayout:
                displayTipDialog();
                break;
            case R.id.PayersRelativeLayout:
                displayPayersDialog();
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

    private void displayTipDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the tip");
        //builder.setCustomTitle()

        // Set up the NumberPicker
        final NumberPicker numberPicker = new NumberPicker(this);
        //String[] values = {"1","2","3"};
        numberPicker.setDisplayedValues(null);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(200);
        numberPicker.setValue(15);
        //numberPicker.setDisplayedValues(values);
        //numberPicker.setValue(Integer.parseInt(values[1]));

        // Set up the NumberPicker's layout parameters
        LinearLayout.LayoutParams pickerParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pickerParams.gravity = Gravity.CENTER;
        numberPicker.setLayoutParams(pickerParams);

        //Set up TextView
        TextView percentText = new TextView(this);
        percentText.setText(getResources().getString(R.string.percent_sign));
        percentText.setTextSize(19);

        // Set up the TextView's layout parameters
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        percentText.setLayoutParams(textParams);

        // Set up the LinearLayout
        LinearLayout layout = new LinearLayout(this);

        // Set up the LinearLayout's layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(params);

        // Add the NumberPicker and TextView to the LinearLayout
        layout.addView(numberPicker);
        layout.addView(percentText);

        // Set the View to the LinearLayout
        builder.setView(layout);

        //TextView defaultTip = (TextView) findViewById(R.id.TipPercentText);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int m_Text = numberPicker.getValue();
                //TODO
                TextView tipPercentText = (TextView) findViewById(R.id.TipValue);
                tipPercentText.setText(m_Text + " " + getResources().getString(R.string.percent_sign));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void displayPayersDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Split the bill");
        //builder.setCustomTitle()

        // Set up the NumberPicker
        final NumberPicker numberPicker = new NumberPicker(this);
        //String[] values = {"1","2","3"};
        numberPicker.setDisplayedValues(null);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(200);
        numberPicker.setValue(1);
        //numberPicker.setDisplayedValues(values);
        //numberPicker.setValue(Integer.parseInt(values[1]));

        // Set up the NumberPicker's layout parameters
        LinearLayout.LayoutParams pickerParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pickerParams.gravity = Gravity.CENTER;
        numberPicker.setLayoutParams(pickerParams);

        //Set up ImageView (person icon)
        ImageView personImage = new ImageView(this);
        personImage.setPadding(12, 12, 12, 12);
        personImage.setImageResource(R.drawable.ic_person_black_24dp);

        // Set up the TextView's layout parameters
        ViewGroup.LayoutParams textParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //personImage.gravity = Gravity.CENTER;
        personImage.setLayoutParams(textParams);

        // Set up the LinearLayout
        LinearLayout layout = new LinearLayout(this);

        // Set up the LinearLayout's layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(params);

        // Add the NumberPicker and TextView to the LinearLayout
        layout.addView(numberPicker);
        layout.addView(personImage);

        // Set the View to the LinearLayout
        builder.setView(layout);

        //TextView defaultTip = (TextView) findViewById(R.id.TipPercentText);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int m_Text = numberPicker.getValue();
                //TODO
                TextView tipPercentText = (TextView) findViewById(R.id.PayersValue);
                tipPercentText.setText(m_Text + " ");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}