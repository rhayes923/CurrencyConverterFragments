/**
 * Ryan Hayes
 * 2/27/20
 * ResultActivity.java
 *
 * This class handles the activity where the currency conversion is displayed.
 */

package edu.quinnipiac.ser210.currencyconverter;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        findViewById(R.id.layout_result).setBackgroundColor(Color.parseColor(MainActivity.color));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String currencyConversion = (String) getIntent().getExtras().get("currencyConversion");
        TextView textView = findViewById(R.id.result);
        textView.setText(currencyConversion);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
