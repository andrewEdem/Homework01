package edu.calvin.akg8.homework01;

/*
* Homework01
* CS-262
* This activity creates a simple calculator app
*
* @author: Andrew Gbeddy
* @version: fall 2016
*/

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static edu.calvin.akg8.homework01.R.*;


public class CalculatorActivity extends Activity
    implements TextView.OnEditorActionListener,OnClickListener,
        AdapterView.OnItemSelectedListener {

    private TextView value1TextView;
    private TextView value2TextView;
    private TextView resultTextView;
    private EditText value1EditText;
    private EditText value2EditText;
    private Button calculateButton;
    private Spinner operatorSpinner;

    private String value1String = "";
    private String value2String = "";
    private String operatorValue;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_calculator);

        value1TextView = (TextView) findViewById(id.value1TextView);
        value2TextView = (TextView) findViewById(id.value2TextView);
        resultTextView = (TextView) findViewById(id.resultTextView);
        value1EditText = (EditText) findViewById(id.value1EditText);
        value2EditText = (EditText) findViewById(id.value2EditText);
        calculateButton = (Button) findViewById(id.calculateButton);
        operatorSpinner = (Spinner) findViewById(id.operatorSpinner);

        calculateButton.setOnClickListener(this);
        operatorSpinner.setOnItemSelectedListener(this);
        value1EditText.setOnEditorActionListener(this);
        value2EditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        // create array for spinner options
        List<String> operator = new ArrayList<String>();
        operator.add("+");
        operator.add("-");
        operator.add("*");
        operator.add("/");

        // create array adapter for specified array and layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, operator);
        // set the layout for the drop-down list
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // set the adapter for the spinner
        operatorSpinner.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        Editor editor = savedValues.edit();
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        calculateAndDisplay();
    }

    public void calculateAndDisplay() {
        value1String = value1EditText.getText().toString();
        value2String = value2EditText.getText().toString();
        operatorValue = operatorSpinner.getSelectedItem().toString();
        float value1;
        float value2;

        if (value1String.equals("")) {
            value1 = 0;
        }
        else {
            value1 = Float.parseFloat(value1String);
        }

        if (value2String.equals("")) {
            value2 = 0;
        }
        else {
            value2 = Float.parseFloat(value2String);
        }
    }


    @Override
    public void onClick(View v) {
        calculateAndDisplay();
        // switch case statement for spinner values
        switch (v.getId())

        {
            case
                    R.id.calculateButton:
                if (operatorValue == "+") {
                    int addition = Integer.parseInt(value1String) + Integer.parseInt(value2String);
                    resultTextView.setText((String.valueOf(addition)));
                }
                if (operatorValue == "-") {
                    int subtraction = Integer.parseInt(value1String) - Integer.parseInt(value2String);
                    resultTextView.setText((String.valueOf(subtraction)));
                }
                if (operatorValue == "*") {
                    int multiplication = Integer.parseInt(value1String) * Integer.parseInt(value2String);
                    resultTextView.setText((String.valueOf(multiplication)));
                }
                if (operatorValue == "/") {
                    int division = Integer.parseInt(value1String) / Integer.parseInt(value2String);
                    resultTextView.setText((String.valueOf(division)));
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onEditorAction(TextView v, int actionID, KeyEvent event) {
        if (actionID == EditorInfo.IME_ACTION_DONE ||
                actionID == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

}
