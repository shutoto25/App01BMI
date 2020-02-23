package com.example.shutoto25.app01bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mCheck;
    TextView mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BMIのスピナーリスト.
        ArrayList<String> listBmi = new ArrayList<>();
        listBmi.add("");
        for (int i = 15; i <= 30; i++) {
            String num = Integer.toString(i);
            listBmi.add(num);
        }
        ArrayAdapter<String> adapterBmi =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listBmi);
        adapterBmi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerBmi = findViewById(R.id.spBmi);
        spinnerBmi.setAdapter(adapterBmi);

        // 体重のスピナーリスト.
        ArrayList<String> listWeight = new ArrayList<>();
        listWeight.add("");
        for (int i = 35; i <= 90; i++) {
            String num = Integer.toString(i);
            listWeight.add(num);
        }
        ArrayAdapter<String> adapterWeight =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listWeight);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerWeight = findViewById(R.id.spWeight);
        spinnerWeight.setAdapter(adapterWeight);

        // 身長のスピナーリスト.
        ArrayList<String> listHeight = new ArrayList<>();
        listHeight.add("");
        for (int i = 140; i <= 190; i++) {
            String num = Integer.toString(i);
            listHeight.add(num);
        }
        ArrayAdapter<String> adapterHeight =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listHeight);
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerHeight = findViewById(R.id.spHeight);
        spinnerHeight.setAdapter(adapterHeight);

        mSummary = findViewById(R.id.tvSummary);
        mCheck = findViewById(R.id.btCheck);
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double bmi = Double.parseDouble(spinnerBmi.getSelectedItem().toString());
                Double weight = Double.parseDouble(spinnerWeight.getSelectedItem().toString());
                Double height = Double.parseDouble(spinnerHeight.getSelectedItem().toString());

                // bmi計算.
                double result = weight / (height / 100 * height / 100);
                // 目標値計算.
                double goal = bmi * (height / 100 * height / 100);
                // 目標達成のための体重.
                double diff = weight - goal;

                BigDecimal resultBd = new BigDecimal(result);
                BigDecimal diffBd = new BigDecimal(diff);

                mSummary.setText(
                        "あなたのBMIは..." + resultBd.setScale(1, BigDecimal.ROUND_HALF_UP) + "です。"
                                + "目標のBMI:" + bmi + "を達成するためには、"
                                + diffBd.setScale(1, BigDecimal.ROUND_UP) + "Kgの増減の必要があります。");
            }
        });
    }
}
