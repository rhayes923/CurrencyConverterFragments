/**
 * Ryan Hayes
 * 2/27/20
 * CurrencyHandler.java
 *
 * This class includes the array of available currencies
 * and converts the JSON data from the REST API to a string that the application can use.
 */

package edu.quinnipiac.ser210.currencyconverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class CurrencyHandler {

    public final static String[] currencies = {"Select Currency", "AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "EUR",
            "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "ISK", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD",
        "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR"};

    public CurrencyHandler() {}

    //Converts the JSONObject from the REST API and breaks it down into usable components
    public String getCurrency(String str) throws JSONException {
        JSONObject currencyJSONObj = new JSONObject(str);
        String from = currencyJSONObj.getString("base_currency_name");
        String amount = currencyJSONObj.getString("amount");

        //Key value of the inner JSONObject is dynamic so a generic key value is needed
        JSONObject rates = currencyJSONObj.getJSONObject("rates");
        Iterator keys = rates.keys();
        String currencyKey = (String) keys.next();
        JSONObject innerJSONObject = rates.getJSONObject(currencyKey);

        String to = innerJSONObject.getString("currency_name");
        String total = innerJSONObject.getString("rate_for_amount");

        String result = from + " converted to " + to + " with an amount of " + amount + " equals " + total;
        return result;
    }
}
