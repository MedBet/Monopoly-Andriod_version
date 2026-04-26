package com.example.myitogproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class DiceActivity extends AppCompatActivity {
TextView _tvRes;
Button _btnkyb,_btnback;
ImageView _imgKyb;
Timer _timer1;
Timer _timer2;
int a,b;
GifImageView _gifKyb;
int imageres;
private SensorManager sm;
private Sensor s;
private SensorEventListener sv;
Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        _imgKyb=(ImageView)findViewById(R.id.imgKyb);
        _btnkyb=(Button) findViewById(R.id.btnKyb);
        _btnback=(Button)findViewById(R.id.btn_back);
        _tvRes=(TextView) findViewById(R.id.tvRes);
        _gifKyb=(GifImageView)findViewById(R.id.gifKyb);
        v=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        _btnback.setEnabled(false);

        _btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(_timer1!=null){
            _timer1.cancel();
        }
        _timer1=new Timer();
        if(_timer2!=null){
            _timer2.cancel();
        }
        _timer2=new Timer();
        int []dice={R.drawable.kyb1,R.drawable.kyb2,R.drawable.kyb3,R.drawable.kyb4,R.drawable.kyb5,R.drawable.kyb6};
        int []dice2={R.drawable.kyb1,R.drawable.kyb2,R.drawable.kyb3,R.drawable.kyb4,R.drawable.kyb5,R.drawable.kyb6};
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sm !=null)s=sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sv = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix,event.values);
                float[] remappedRotationMatrix= new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_X,SensorManager.AXIS_Z,remappedRotationMatrix);
                float[] oreintations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix,oreintations);
                for(int i=0;i<3;i++){
                    oreintations[i]=(float) (Math.toDegrees(oreintations[i]));

                }
                _imgKyb.setRotation(oreintations[2]);
                if (oreintations[2]>45|| oreintations[2]<-45){
                    //_imgKyb.setImageResource(R.drawable.p1);
                    _gifKyb.setVisibility(View.VISIBLE);
                    int diceRoll = getIntent().getIntExtra("diceRoll", 0);
                    //_tvRes.setText(String.valueOf(diceRoll));
                    imageres=dice[diceRoll-1];


                    v.vibrate(5);
                    _timer1.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _gifKyb.setVisibility(View.GONE);
                                    _imgKyb.setImageResource(imageres);
                                    _btnback.setEnabled(true);
                                }
                            });
                        }
                    },2000,1000);

                }
//                if(oreintations[2]>=0 && oreintations[2]<=5|| oreintations[2]<=-5){
//                    _gifKyb.setVisibility(View.GONE);
//                    _imgKyb.setImageResource(imageres);
//                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };



        _btnkyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diceRoll = getIntent().getIntExtra("diceRoll", 0);
                _tvRes.setText(String.valueOf(diceRoll));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sv,s,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sv);
        _timer1.cancel();
    }
}