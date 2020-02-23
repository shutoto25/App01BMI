package com.example.shutoto25.app01bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    Spinner mSpinnerBmi;
    Spinner mSpinnerWeight;
    Spinner mSpinnerHeight;
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
        mSpinnerBmi = findViewById(R.id.spBmi);
        mSpinnerBmi.setAdapter(adapterBmi);

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
        mSpinnerWeight = findViewById(R.id.spWeight);
        mSpinnerWeight.setAdapter(adapterWeight);

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
        mSpinnerHeight = findViewById(R.id.spHeight);
        mSpinnerHeight.setAdapter(adapterHeight);

        mSummary = findViewById(R.id.tvSummary);
        mCheck = findViewById(R.id.btCheck);
        mCheck.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        // 面倒だったのでまとめてトースト表示
        // 計算→表示がごちゃごちゃしてて気持ち悪いけど
        // どうしたらいい感じにまとまるのかわからんかったからとりあえず動くものを.
        try {
            double bmi = Double.parseDouble(mSpinnerBmi.getSelectedItem().toString());
            double weight = Double.parseDouble(mSpinnerWeight.getSelectedItem().toString());
            double height = Double.parseDouble(mSpinnerHeight.getSelectedItem().toString());

            // bmi計算.
            double result = weight / ((height / 100) * (height / 100));
            // 目標体重計算.
            double targetWeight = bmi * ((height / 100) * (height / 100));
            // 目標達成までの差分.
            double diff = targetWeight - weight;

            BigDecimal resultBd = new BigDecimal(result);
            BigDecimal diffBd = new BigDecimal(diff);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.result_text_bmi, resultBd.setScale(1, BigDecimal.ROUND_HALF_UP)));
            stringBuilder.append(getString(R.string.result_text_purpose_bmi, String.valueOf(bmi)));
            if (diff > 0) {
                // 増やす必要がある人
                stringBuilder.append(getString(R.string.result_text_diff_minus, diffBd.setScale(1, BigDecimal.ROUND_UP)));
            } else {
                // ダイエットが必要な人
                stringBuilder.append(getString(R.string.result_text_diff_plus, diffBd.setScale(1, BigDecimal.ROUND_UP)));
            }
            mSummary.setText(new String(stringBuilder));

        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.toast_error_message, Toast.LENGTH_SHORT).show();
        }
    }
}
