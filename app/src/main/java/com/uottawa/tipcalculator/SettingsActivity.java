package com.uottawa.tipcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up Settings ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.settingsmenu_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our "layout buttons"
        RelativeLayout defaultTipButton = (RelativeLayout) findViewById(R.id.DefaultTip);
        RelativeLayout currencyButton = (RelativeLayout) findViewById(R.id.Currency);

        // Create background
        final int[] attrs = new int[]{R.attr.selectableItemBackground};
        final TypedArray typedArray = this.obtainStyledAttributes(attrs);
        final int backgroundResource = typedArray.getResourceId(0, 0);

        // Apply background to our "layout buttons" (for when they are pressed)
        defaultTipButton.setBackgroundResource(backgroundResource);
        currencyButton.setBackgroundResource(backgroundResource);
        typedArray.recycle();

        // Apply User Settings
        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), MODE_PRIVATE);
        String defaultTip = preferences.getString(getString(R.string.PREFERENCE_DEFAULT_TIP_KEY), "15");
        String currency = preferences.getString(getString(R.string.PREFERENCE_CURRENCY_KEY), getString(R.string.dollar_sign));

        ((TextView) findViewById(R.id.DefaultTipValue)).setText(defaultTip);
        ((TextView) findViewById(R.id.CurrencySymbolText)).setText(" " + currency);

    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        saveSettings();
        return super.onSupportNavigateUp();
    }

    private void saveSettings() {
        // Show "Saved" toast
        Toast savedToast = Toast.makeText(getApplicationContext(), "Saved Settings", Toast.LENGTH_SHORT);
        savedToast.setGravity(Gravity.BOTTOM, 0, 10);
        savedToast.show();

        // Save the settings to the key-value storage
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.PREFERENCE_DEFAULT_TIP_KEY),((TextView) findViewById(R.id.DefaultTipValue)).getText().toString());
        editor.putString(getString(R.string.PREFERENCE_CURRENCY_KEY), ((TextView) findViewById(R.id.CurrencySymbolText)).getText().toString());
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.DefaultTip:
                displayTipDialog(view);
                break;
            case R.id.Currency:
                displayCurrencyDialog(view);
                break;
        }
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
        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), MODE_PRIVATE);
        String defaultTip = preferences.getString(getString(R.string.PREFERENCE_DEFAULT_TIP_KEY), "15");
        numberPicker.setValue(Integer.parseInt(defaultTip));
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
                TextView tipPercentText = (TextView) findViewById(R.id.DefaultTipValue);
                tipPercentText.setText(Integer.toString(m_Text));
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

    private void displayCurrencyDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the currency");
        //builder.setCustomTitle()

        // Create White Space between Title and RadioButtons
        ViewGroup.LayoutParams spaceParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 20);
        final Space space = new Space(this);
        space.setLayoutParams(spaceParams);

        // Set up the RadioButtons
        final RadioButton dollarRadioButton = new RadioButton(this);
        dollarRadioButton.setId(R.id.radio_button_dollar);
        dollarRadioButton.setText(getResources().getString(R.string.dollar_sign));
        dollarRadioButton.setTextSize(19);
        //dollarRadioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final RadioButton euroRadioButton = new RadioButton(this);
        euroRadioButton.setId(R.id.radio_button_euro);
        euroRadioButton.setText(getResources().getString(R.string.euro_sign));
        euroRadioButton.setTextSize(19);
        //euroRadioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final RadioButton poundRadioButton = new RadioButton(this);
        poundRadioButton.setId(R.id.radio_button_pound);
        poundRadioButton.setText(getResources().getString(R.string.pound_sign));
        poundRadioButton.setTextSize(19);
        //poundRadioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Set up the RadioGroup
        final RadioGroup radioGroup = new RadioGroup(this);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams
                (RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setGravity(Gravity.CENTER);
        radioGroup.setLayoutParams(params);
        radioGroup.addView(space);
        radioGroup.addView(dollarRadioButton);
        radioGroup.addView(euroRadioButton);
        radioGroup.addView(poundRadioButton);

        // Set the View to the RadioGroup
        builder.setView(radioGroup);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO
                TextView currencySymbolText = (TextView) findViewById(R.id.CurrencySymbolText);
                switch(radioGroup.getCheckedRadioButtonId()){
                    case R.id.radio_button_dollar:
                        currencySymbolText.setText(getResources().getString(R.string.dollar_sign));
                        break;
                    case R.id.radio_button_euro:
                        currencySymbolText.setText(getResources().getString(R.string.euro_sign));
                        break;
                    case R.id.radio_button_pound:
                        currencySymbolText.setText(getResources().getString(R.string.pound_sign));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "An error occurred. Currency not changed.", Toast.LENGTH_SHORT).show();
                        break;
                }
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
