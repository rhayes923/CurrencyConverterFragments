/**
 * Ryan Hayes
 * 3/30/20
 * ResultFragment.java
 *
 * This is the result fragment that displays the currency conversion that the user had selected.
 */

package edu.quinnipiac.ser210.currencyconverter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultFragment extends Fragment {

    private String conversion;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    public void setConversionText(String conversion) {
        this.conversion = conversion;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.result);
            textView.setText(conversion);
        }
    }
}
