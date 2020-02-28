/**
 * Ryan Hayes
 * 2/27/20
 * MainActivity.java
 *
 * This is the main java class that handles most of the application including AsyncTask.
 */

package edu.quinnipiac.ser210.currencyconverter;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.ShareActionProvider;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    CurrencyHandler crHandler = new CurrencyHandler();
    ShareActionProvider provider;
    boolean userSelect = false;
    private String url1 = "https://currency-converter5.p.rapidapi.com/currency/convert";
    private String url2 = "&format=json";
    protected static String color = "#ffffff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Button button = findViewById(R.id.button);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, crHandler.currencies);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(currencyAdapter);
        spinner2.setAdapter(currencyAdapter);

        //Listener for the conversion button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSelect) {
                    Spinner spinner1 = findViewById(R.id.spinner1);
                    Spinner spinner2 = findViewById(R.id.spinner2);
                    EditText editText = findViewById(R.id.editText);
                    String option1 = spinner1.getSelectedItem().toString();
                    String option2 = spinner2.getSelectedItem().toString();
                    int amount = Integer.valueOf(editText.getText().toString());
                    if (option1 != "Select Currency" && option2 != "Select Currency") {
                        String params = "?from=" + option1 + "&to=" + option2 + "&amount=" + amount;
                        new FetchConversion().execute(params);
                    }
                }
            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Cycles background color based on current color
        if (id == R.id.action_change_color) {
            LinearLayout layout = findViewById(R.id.layout_main);
            if (color == "#ffffff") {
                layout.setBackgroundColor(Color.parseColor("#aaffc3"));
                color = "#aaffc3";
            } else if (color == "#aaffc3") {
                layout.setBackgroundColor(Color.parseColor("#fabebe"));
                color = "#fabebe";
            } else if (color == "#fabebe") {
                layout.setBackgroundColor(Color.parseColor("#fffac8"));
                color = "#fffac8";
            } else if (color == "#fffac8") {
                layout.setBackgroundColor(Color.parseColor("#78bbeb"));
                color = "#78bbeb";
            } else if (color == "#78bbeb") {
                layout.setBackgroundColor(Color.parseColor("#ffffff"));
                color = "#ffffff";
            }
            return true;
        }

        //Creates the help and information activity
        if (id == R.id.action_text) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
            return true;
        }

        //Creates the intent for the share button
        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hi");
            if (provider != null) {
                provider.setShareIntent(intent);
            } else {
                Toast.makeText(this, "No Provider", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    //AsyncTask subclass that handles background tasks and connecting to the REST API
    private class FetchConversion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            String currencyConversion = "";
            try {
                URL url = new URL(url1 + strings[0] + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "1dcc21473emshfabcff718c1d17cp180dbfjsn40b5ca92a267");
                urlConnection.connect();
                if (urlConnection.getInputStream() == null) {
                    return null;
                }
                currencyConversion = getStringFromBuffer(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())));
            } catch (Exception e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return currencyConversion;
        }

        private String getStringFromBuffer(BufferedReader bufferedReader) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + '\n');
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e){
                    return null;
                }
            }
            return crHandler.getCurrency(buffer.toString());
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("currencyConversion", result);
                startActivity(intent);
            }
        }
    }
}
