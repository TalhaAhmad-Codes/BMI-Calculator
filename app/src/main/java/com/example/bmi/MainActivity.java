package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare UI elements
    EditText weight, heightFeet;
    Spinner heightInchesS;
    Button btnCalculate;
    TextView txtBmiValue, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        weight = findViewById(R.id.weightInput);
        heightFeet = findViewById(R.id.heightFeetInput);
        heightInchesS = findViewById(R.id.heightInchesSpinner);
        btnCalculate = findViewById(R.id.calculateButton);
        txtBmiValue = findViewById(R.id.bmiValueOutput);
        txtStatus = findViewById(R.id.bmiStatusOutput);

        // Setup the "Height - Inches" spinner with values 0-12
        ArrayList<Integer> inches = new ArrayList<Integer>();
        for (int i = 0; i <= 12; i++){
            inches.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, inches);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heightInchesS.setAdapter(adapter);

        // Calculate BMI
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String wStr = weight.getText().toString();
        String fStr = heightFeet.getText().toString();

        if (wStr.isEmpty() || fStr.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert inputs to numbers
        float weightKg = Float.parseFloat(wStr);
        int feet = Integer.parseInt(fStr);
        int inches = (int) heightInchesS.getSelectedItem();

        // Formula: 1 foot = 0.3048 meters, 1 inch = 0.0254 meters
        double totalHeightMeters = (feet * 0.3048) + (inches * 0.0254);

        if (totalHeightMeters > 0) {
            double bmi = weightKg / (totalHeightMeters * totalHeightMeters);
            displayResult(bmi);
        }
    }

    private void displayResult(double bmi) {
        // Update the number text
        String value = String.format("%.2f", bmi);
        txtBmiValue.setText(value);

        // Update the status text
        if (bmi < 18.5) {
            txtStatus.setText("Underweight");
        } else if (bmi >= 18.5 && bmi < 24.9) {
            txtStatus.setText("Normal Weight");
        } else if (bmi >= 25 && bmi < 29.9) {
            txtStatus.setText("Overweight");
        } else {
            txtStatus.setText("Obese");
        }

        if (bmi > 30.0){
            txtStatus.setText("Obese");
        }
        else if (bmi > 25.0){
            txtStatus.setText("Overweight");
        }
        else if (bmi > 18.5){
            txtStatus.setText("Healthy Weight");
        }
        else{
            txtStatus.setText("Underweight");
        }
    }
}
