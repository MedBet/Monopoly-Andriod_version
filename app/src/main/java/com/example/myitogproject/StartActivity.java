package com.example.myitogproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
TextView _tv_chPlayer;
SeekBar _seekBar;
Button _btn_Start;
EditText _ed_name;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        _ed_name=(EditText)findViewById(R.id.ed_name);

        _btn_Start=(Button)findViewById(R.id.btn_Start);


        _btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    // Создаем Intent для перехода к MainActivity
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);

                    // Передаем значение player в MainActivity
                    intent.putExtra("name", _ed_name.getText().toString());

                    // Запускаем MainActivity
                    startActivity(intent);

            }
        });
    }
}