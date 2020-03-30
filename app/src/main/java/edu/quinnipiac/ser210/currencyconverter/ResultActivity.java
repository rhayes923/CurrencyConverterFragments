/**
 * Ryan Hayes
 * 3/30/20
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

public class ResultActivity extends AppCompatActivity {

    private String conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        findViewById(R.id.layout_result).setBackgroundColor(Color.parseColor(MainActivity.color));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        conversion = getIntent().getExtras().getString("currencyConversion");
        ResultFragment resultFragment = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_result);
        resultFragment.setConversionText(conversion);

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
