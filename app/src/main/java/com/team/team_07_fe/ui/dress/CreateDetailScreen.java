package com.team.team_07_fe.ui.dress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.team.team_07_fe.R;

public class CreateDetailScreen extends AppCompatActivity {
    TextInputEditText InputDate,InputName, InputMoney, InputText;
    AppCompatButton BtnAddDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputDate = findViewById(R.id.create_detail_input_date);
        InputName = findViewById(R.id.create_detail_input_name);
        InputMoney = findViewById(R.id.create_detai_input_money);
        InputText = findViewById(R.id.create_detai_input_text);

        BtnAddDetail = findViewById(R.id.btn_add_detail);


    }

}