package com.uottawa.tipcalculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RelativeLayout defaultTip = (RelativeLayout) findViewById(R.id.DefaultTip);
        final int[] attrs = new int[]{R.attr.selectableItemBackground};
        final TypedArray typedArray = this.obtainStyledAttributes(attrs);
        final int backgroundResource = typedArray.getResourceId(0, 0);
        defaultTip.setBackgroundResource(backgroundResource);
        typedArray.recycle();

        /*
        defaultTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You pressed the default tip view", Toast.LENGTH_SHORT).show();
            }
        });
        */

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.CalculateButton:
                Toast.makeText(getApplicationContext(), "You pressed the calculate button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.DefaultTip:
                Toast.makeText(getApplicationContext(), "You pressed the default tip view", Toast.LENGTH_SHORT).show();
                displayTipDialog(v);
                break;
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        //Dialog.setContentView(R.layout.tip_spinner);
    }

    private void displayTipDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the default tip");
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
                TextView tipPercentText = (TextView) findViewById(R.id.TipPercentText);
                tipPercentText.setText(m_Text + " " + getResources().getString(R.string.percent_sign));
                Toast.makeText(getApplicationContext(), "You entered " + m_Text, Toast.LENGTH_SHORT).show();
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


    /*
    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.DefaultTip:
                Toast.makeText(getApplicationContext(), "You pressed the default tip view", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    */
}
