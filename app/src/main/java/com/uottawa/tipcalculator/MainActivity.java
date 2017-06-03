package com.uottawa.tipcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mainmenu_layout);

        // Find our "Layout Buttons"
        RelativeLayout priceLayoutButton = (RelativeLayout) findViewById(R.id.PriceRelativeLayout);
        RelativeLayout tipLayoutButton = (RelativeLayout) findViewById(R.id.TipRelativeLayout);
        RelativeLayout payersLayoutButton = (RelativeLayout) findViewById(R.id.PayersRelativeLayout);

        // Create background
        final int[] attrs = new int[]{R.attr.selectableItemBackground};
        final TypedArray typedArray = this.obtainStyledAttributes(attrs);
        final int backgroundResource = typedArray.getResourceId(0, 0);

        // Apply background to our "layout buttons" (for when they are pressed)
        priceLayoutButton.setBackgroundResource(backgroundResource);
        tipLayoutButton.setBackgroundResource(backgroundResource);
        payersLayoutButton.setBackgroundResource(backgroundResource);
        typedArray.recycle();

        // Apply User Settings
        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), MODE_PRIVATE);
        String defaultTip = preferences.getString(getString(R.string.PREFERENCE_DEFAULT_TIP_KEY), "15");
        String currency = preferences.getString(getString(R.string.PREFERENCE_CURRENCY_KEY), getString(R.string.dollar_sign));

        ((TextView) findViewById(R.id.TipValue)).setText(defaultTip);
        ((TextView) findViewById(R.id.TipValue2)).setText(defaultTip);
        ((TextView) findViewById(R.id.CurrencySymbolText)).setText(currency + " ");
        ((TextView) findViewById(R.id.CurrencySymbolText2)).setText(currency + " ");
        ((TextView) findViewById(R.id.CurrencySymbolText3)).setText(currency + " ");
        ((TextView) findViewById(R.id.CurrencySymbolText4)).setText(currency + " ");

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.PriceRelativeLayout:
                displayPriceDialog();
            break;
            case R.id.TipRelativeLayout:
                displayTipDialog();
                break;
            case R.id.SuggestTipButton:
                displaySuggestTipDialog();
                break;
            case R.id.PayersRelativeLayout:
                displayPayersDialog();
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
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menu_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }
        return true;
    }

    private void displayPriceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the total price of the bill");
        //builder.setCustomTitle()

        // Set up TextView
        final TextView currencySymbolText = new TextView(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
        currencySymbolText.setText(sharedPreferences.getString(getString(R.string.PREFERENCE_CURRENCY_KEY), getString(R.string.dollar_sign)));
        currencySymbolText.setTextSize(19);
        currencySymbolText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));

        // Set up EditText
        final EditText priceText = new EditText(this);
        priceText.setText(((TextView) findViewById(R.id.PriceValue)).getText().toString());
        priceText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        priceText.setTextSize(19);
        priceText.requestFocus();

        // Set up the EditText's layout parameters
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        priceText.setLayoutParams(textParams);
        priceText.addTextChangedListener(new CurrencyTextWatcher());

        // Set up the LinearLayout
        LinearLayout layout = new LinearLayout(this);
        layout.setPadding(40, 10, 40, 10);

        // Set up the LinearLayout's layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(params);

        // Add the EditText and TextView to the LinearLayout
        layout.addView(currencySymbolText);
        layout.addView(priceText);

        // Set the View to the LinearLayout
        builder.setView(layout);


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = priceText.getText().toString();
                //TODO
                TextView priceValueText = (TextView) findViewById(R.id.PriceValue);
                TextView priceValueText2 = (TextView) findViewById(R.id.PriceValue2);
                priceValueText.setText(m_Text);
                priceValueText2.setText(m_Text);

                updateTotals();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
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
        numberPicker.setValue(Integer.parseInt(((TextView) findViewById(R.id.TipValue)).getText().toString()));
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
                tipPercentText.setText(Integer.toString(m_Text));
                TextView tipPercentText2 = (TextView) findViewById(R.id.TipValue2);
                tipPercentText2.setText(Integer.toString(m_Text));

                updateTotals();
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

    private void displaySuggestTipDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate your experience");
        //builder.setCustomTitle()

        // Set up the NumberPicker
        final RatingBar ratingBar = new RatingBar(this);
        ratingBar.setNumStars(5);

        // Set up the NumberPicker's layout parameters
        ViewGroup.LayoutParams ratingBarParams = new ViewGroup.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //ratingBarParams.gravity = Gravity.CENTER;
        ratingBar.setLayoutParams(ratingBarParams);

        //Set up TextView
        final TextView percentText = new TextView(this);
        percentText.setText(getResources().getString(R.string.percent_sign));
        percentText.setTextSize(19);
        percentText.setText("Recommended tip: " + (int) calculateSuggestedTip(ratingBar.getRating()) + " " + getResources().getString(R.string.percent_sign));

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
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(params);

        // Add the NumberPicker and TextView to the LinearLayout
        layout.addView(ratingBar);
        layout.addView(percentText);

        // Set the View to the LinearLayout
        builder.setView(layout);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                percentText.setText("Recommended tip: " + calculateSuggestedTip(ratingBar.getRating()) + " " + getResources().getString(R.string.percent_sign));
            }
        });

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int m_Text = (int) calculateSuggestedTip(ratingBar.getRating());
                percentText.setText("Recommended tip: " + m_Text + " " + getResources().getString(R.string.percent_sign));
                TextView tipPercentText = (TextView) findViewById(R.id.TipValue);
                tipPercentText.setText(Integer.toString(m_Text));
                TextView tipPercentText2 = (TextView) findViewById(R.id.TipValue2);
                tipPercentText2.setText(Integer.toString(m_Text));

                updateTotals();
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

    private static int calculateSuggestedTip(float rating) {
        return (int)(10 + (2 * rating));
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
        numberPicker.setValue(Integer.parseInt(((TextView) findViewById(R.id.PayersValue)).getText().toString()));
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
                TextView payersText = (TextView) findViewById(R.id.PayersValue);
                payersText.setText(Integer.toString(m_Text));
                TextView payersText2 = (TextView) findViewById(R.id.PayersValue2);
                payersText2.setText(Integer.toString(m_Text));

                updateTotals();
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

    private void updateTotals() {
        float priceValue = Float.parseFloat(((TextView) findViewById(R.id.PriceValue)).getText().toString());
        float tipValue = Float.parseFloat(((TextView) findViewById(R.id.TipValue)).getText().toString());
        float numPersons = Float.parseFloat(((TextView) findViewById(R.id.PayersValue)).getText().toString());
        float totalValue = priceValue * (1 + tipValue/100);

        DecimalFormat decimal = new DecimalFormat("###.##");

        ((TextView) findViewById(R.id.TotalValue)).setText(decimal.format(totalValue));
        ((TextView) findViewById(R.id.PricePerPersonValue)).setText(decimal.format(totalValue/numPersons));
    }

}