package com.example.myitogproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class EndActivity extends AppCompatActivity {
    TextView _tv_endres;
    Button _btn_restart, _btnstats;
    GifImageView _gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        _tv_endres=(TextView) findViewById(R.id.tv_endres);
        _btn_restart=(Button) findViewById(R.id.btn_restart);
        _btnstats=(Button)findViewById(R.id.btnstats);
        _gif=(GifImageView)findViewById(R.id.gif);
        String pl1 = getIntent().getStringExtra("name");
        int nichya = getIntent().getIntExtra("nichya", 0);
        int p1gameover = getIntent().getIntExtra("p1gameover", 0);
        int p2gameover = getIntent().getIntExtra("p2gameover", 0);

        if(nichya==3){
            _tv_endres.setText("К сожалению никто не победил");
            _gif.setImageResource(R.drawable.lose);
            Intent intent = new Intent(EndActivity.this, MainActivitybase.class);
            intent.putExtra("nicha", 0);

        }
        if(p1gameover==2){
            _tv_endres.setText("Победил Игрок 2");
            _gif.setImageResource(R.drawable.p2win);
        }
        if(p2gameover==1){
            _tv_endres.setText("Победил "+pl1);
            _gif.setImageResource(R.drawable.p1win);



        }

        _btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        _btnstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EndActivity.this, MainActivitybase.class);
                if(p2gameover==1){
                    intent1.putExtra("win2", 1);
                    intent1.putExtra("name2", pl1);

                }
                if (p1gameover==2){
                    intent1.putExtra("win2", 0);
                    intent1.putExtra("name2", pl1);
                }

                startActivity(intent1);
            }
        });
    }
}