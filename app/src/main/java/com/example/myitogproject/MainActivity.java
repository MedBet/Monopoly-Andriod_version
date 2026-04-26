package com.example.myitogproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int player;
    int player2;
    TextView _tv_kolPlayer,_tvPlayer,_tv_dengi1,_tv_dengi2;
    Button _btn_Kybik,_btn_Buy;
    int diceRoll,igrok=1;
    int diceRoll2;
    int playr=2;
    Timer _timer1;
    int a = 0, b=0,c=0;
    int sum=0;
    int dengi1=1000,dengi2=1000;
    int renta=50;
    int perbros=0;
    int resultat=0;
    boolean player2inJail=false;
    int buy[]={0,0,0,0,0,0,0,0};
    boolean isPlayer1Visible = true;
    TextView _tv_res,_pl2,_tv_res2;
    RelativeLayout lay;
    ImageView p1,p2;
    private ImageButton[] cells; // Массив для хранения ячеек
    int newPositionIndex;
    int newPositionIndex2;
    String[] cities = {"Чебоксары", "Псков", "Санкт-Петербург", "Самара", "Красноярск", "Пенза", "Воронеж", "Челябинск"};
    String[] shans = {"1","У вас появились конкуренты. Для продвижения своего бизнеса вам надо заплатить 100руб.","Идите на поле ВПЕРЁД","Вам повезло. Вы ничего не получаете"};
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPlayer1Visible=true;
        //p1.setVisibility(View.VISIBLE);





        _tvPlayer=(TextView) findViewById(R.id.tv_Player);
        _pl2=(TextView) findViewById(R.id.pl2);
        _tv_res=(TextView) findViewById(R.id.tv_res);
        _tv_dengi1=(TextView) findViewById(R.id.tv_dengi1);
        _tv_dengi2=(TextView) findViewById(R.id.tv_dengi2);
        _tv_res2=(TextView) findViewById(R.id.tv_res2);
        lay=(RelativeLayout)findViewById(R.id.lay);
        p1=(ImageView)findViewById(R.id.player1);
        p2=(ImageView)findViewById(R.id.player2);

        String pl1 = getIntent().getStringExtra("name");
        _tvPlayer.setText(pl1);

        cells = new ImageButton[16];
        cells[0] = findViewById(R.id.cell9);
        cells[1] = findViewById(R.id.cell10);
        cells[2] = findViewById(R.id.cell11);
        cells[3] = findViewById(R.id.cell12);
        cells[4] = findViewById(R.id.cell13);
        cells[5] = findViewById(R.id.cell14);
        cells[6] = findViewById(R.id.cell15);
        cells[7] = findViewById(R.id.cell16);
        cells[8] = findViewById(R.id.cell1);
        cells[9] = findViewById(R.id.cell2);
        cells[10] = findViewById(R.id.cell3);
        cells[11] = findViewById(R.id.cell4);
        cells[12] = findViewById(R.id.cell5);
        cells[13] = findViewById(R.id.cell6);
        cells[14] = findViewById(R.id.cell7);
        cells[15] = findViewById(R.id.cell8);

        _btn_Kybik=(Button) findViewById(R.id.btn_Kybik);
        _btn_Kybik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _btn_Kybik.setEnabled(false);
                int randomNumber = (int) (Math.random() * 6) + 1;
                int randomNumber2 = (int) (Math.random() * 6) + 1;

                diceRoll += randomNumber;
                diceRoll2 += randomNumber2;
                if(player2inJail==true){
                    diceRoll2=4;
                    _btn_Kybik.setEnabled(true);
                    c=1;
                    if(c==1){
                        player2inJail=false;
                    }

                }
                if (diceRoll > 16) {
                    diceRoll -= 16;
                }
                if (diceRoll2 > 16) {
                    diceRoll2 -= 16;
                }
                //Toast.makeText(MainActivity.this, "Generated Number: " + randomNumber + "\nCurrent Sum: " + sum, Toast.LENGTH_SHORT).show();


                if (a == 0) { // Первый бросок кубика
                    newPositionIndex = (diceRoll) % cells.length;
                    a = 1;
                } else { // Последующие броски кубика

                    newPositionIndex = (diceRoll) % cells.length;
                    if (newPositionIndex >= 16 && player >= 16) {
                        newPositionIndex = 0;
                        player = 0;
                    }
                }
                if (b == 0) { // Первый бросок кубика
                    newPositionIndex2 = (diceRoll2) % cells.length;
                    b = 1;
                } else { // Последующие броски кубика

                    newPositionIndex2 = (diceRoll2) % cells.length;
                    if (newPositionIndex2 >= 16 && player2 >= 16) {
                        newPositionIndex2 = 0;
                        player2 = 0;
                    }
                }

                ImageButton newCell = cells[newPositionIndex];
                ImageButton newCell2 = cells[newPositionIndex2];// -1, так как индексы начинаются с 0

                // Устанавливаем максимальные размеры изображения
                int maxWidth = 70; // Ширина ячейки
                int maxHeight = 70; // Высота ячейки
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
                Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);// Замените "your_image" на реальный идентификатор вашего изображения
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, maxWidth, maxHeight, true);
                Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, maxWidth, maxHeight, true);

                // Устанавливаем изображение для p1
                p1.setImageBitmap(scaledBitmap);
                p2.setImageBitmap(scaledBitmap1);

                // Устанавливаем правила размещения для p1 относительно новой ячейки
                params.addRule(RelativeLayout.ALIGN_TOP, newCell.getId());
                params.addRule(RelativeLayout.ALIGN_LEFT, newCell.getId());

                if(perbros==0){
                    p1.setVisibility(View.INVISIBLE);
                }


                // Устанавливаем новые параметры для p1


                player = diceRoll;
                player2 = diceRoll2;
                isPlayer1Visible=false;
                perbros++;
                for (int i = 0; i < cells.length; i++) {
                    final int cellIndex = i;
                    cells[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Если игрок 2 кликнул на клетку, в которой находится игрок 1
                            if (cellIndex == newPositionIndex && !isPlayer1Visible) {
                                // Сделать игрока 1 видимым
                                p1.setVisibility(View.VISIBLE);
                                p1.setLayoutParams(params);
                                isPlayer1Visible = true;



                                if (newPositionIndex==4){
                                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("Вы в тюрьме").setMessage("вы пропускаете ход").setCancelable(false)
                                            .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    Handler handler = new Handler();
                                                    long delayMillis = 5000;
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            _tv_res.append(pl1+" попал в тюрьму"+"\n");
                                                            int randomNumber2 = (int) (Math.random() * 6) + 1;
                                                            _pl2.setText(String.valueOf(randomNumber2));
                                                            diceRoll2 += randomNumber2;

                                                            if (diceRoll2 > 16) {
                                                                diceRoll2 -= 16;
                                                            }
                                                            //Toast.makeText(MainActivity.this, "Generated Number: " + randomNumber + "\nCurrent Sum: " + sum, Toast.LENGTH_SHORT).show();



                                                            if (b == 0) { // Первый бросок кубика
                                                                newPositionIndex2 = (diceRoll2) % cells.length;
                                                                b = 1;
                                                            } else { // Последующие броски кубика

                                                                newPositionIndex2 = (diceRoll2) % cells.length;
                                                                if (newPositionIndex2 >= 16 && player2 >= 16) {
                                                                    newPositionIndex2 = 0;
                                                                    player2 = 0;
                                                                }
                                                            }


                                                            ImageButton newCell2 = cells[newPositionIndex2];// -1, так как индексы начинаются с 0

                                                            // Устанавливаем максимальные размеры изображения
                                                            int maxWidth = 70; // Ширина ячейки
                                                            int maxHeight = 70; // Высота ячейки

                                                            Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);// Замените "your_image" на реальный идентификатор вашего изображения

                                                            Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, maxWidth, maxHeight, true);

                                                            // Устанавливаем изображение для p1

                                                            p2.setImageBitmap(scaledBitmap1);

                                                            // Устанавливаем правила размещения для p1 относительно новой ячейки

                                                            params2.addRule(RelativeLayout.ALIGN_TOP, newCell2.getId());
                                                            params2.addRule(RelativeLayout.ALIGN_RIGHT, newCell2.getId());

                                                            // Устанавливаем новые параметры для p1
                                                            _tv_res2.append("Игрок 2 походил на клетку "+ newPositionIndex2+ "\n");
                                                            p2.setLayoutParams(params2);
                                                            int rnd = (int) (Math.random() * 6) + 1;
                                                            player2 = diceRoll2;
                                                            if(newPositionIndex2<=2&& newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-1]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-1]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-1]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-1]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }


                                                            }
                                                            if(newPositionIndex2>=5 && newPositionIndex2<=7 && newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-3]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-3]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-3]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-3]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }

                                                            }
                                                            if(newPositionIndex2==14){
                                                                _tv_res2.append("Игрок 2 заплатил налог на роскошь "+ "\n");
                                                                dengi2-=75;
                                                                _tv_dengi2.setText(String.valueOf(dengi2));

                                                            }
                                                            if(newPositionIndex2>=9 && newPositionIndex2<=11&& newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-4]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-4]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-4]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-4]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }

                                                            }
                                                        }

                                                    }, delayMillis);






                                                }
                                            });
                                    AlertDialog alert=builder.create();
                                    alert.show();
                                }
                                if (newPositionIndex<=2&& newPositionIndex!=0){
                                    if(buy[newPositionIndex-1]==0){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Вы хотите купить?").setMessage("вы можете купить "+cities[newPositionIndex-1]+" за 100руб.").setCancelable(false)
                                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();




                                                    }
                                                });
                                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                buy[newPositionIndex-1]=1;
                                                Toast.makeText(MainActivity.this, "у вас списалось 100р.", Toast.LENGTH_SHORT).show();
                                                _tv_res.append(pl1+" купил "+cities[newPositionIndex-1]+ "\n");
                                                dengi1-=100;
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }
                                        });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                    if(buy[newPositionIndex-1]==2){
                                        _tv_res.append(pl1+" заплатил ренту"+ "\n");
                                        dengi1-=50;
                                        dengi2+=50;
                                        _tv_dengi1.setText(String.valueOf(dengi1));
                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                    }

                                }
                                if (newPositionIndex>=5 && newPositionIndex<=7 && newPositionIndex!=0){
                                    if(buy[newPositionIndex-3]==0){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Вы хотите купить?").setMessage("вы можете купить "+cities[newPositionIndex-3]+" за 100руб.").setCancelable(false)
                                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();




                                                    }
                                                });
                                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                buy[newPositionIndex-3]=1;
                                                Toast.makeText(MainActivity.this, "у вас списалось 100р.", Toast.LENGTH_SHORT).show();
                                                _tv_res.append(pl1+" купил "+cities[newPositionIndex-3]+ "\n");
                                                dengi1-=100;
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }
                                        });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                    if(buy[newPositionIndex-3]==2){
                                        _tv_res.append(pl1+" заплатил ренту"+ "\n");
                                        dengi1-=50;
                                        dengi2+=50;
                                        _tv_dengi1.setText(String.valueOf(dengi1));
                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                    }
                                }
                                if(newPositionIndex==14){
                                    _tv_res.append(pl1+" заплатил налог на роскошь "+ "\n");
                                    dengi1-=75;
                                    _tv_dengi1.setText(String.valueOf(dengi1));

                                }
                                if(newPositionIndex==3||newPositionIndex==13|| newPositionIndex==15){
                                    _tv_res.append(pl1+" взял шанс "+ "\n");
                                    int rnd = (int) (Math.random() * 3) + 1;
                                    if(rnd==1){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Шанс").setMessage(shans[rnd]).setCancelable(false)
                                                .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dengi1-=100;
                                                        _tv_dengi1.setText(String.valueOf(dengi1));
                                                    }
                                                });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                    if(rnd==2){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Шанс").setMessage(shans[rnd]).setCancelable(false)
                                                .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        diceRoll=0;
                                                        newPositionIndex=0;
                                                        ImageButton newCell = cells[newPositionIndex];
                                                        // -1, так как индексы начинаются с 0

                                                        // Устанавливаем максимальные размеры изображения
                                                        int maxWidth = 70; // Ширина ячейки
                                                        int maxHeight = 70; // Высота ячейки
                                                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p1);

                                                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, maxWidth, maxHeight, true);


                                                        // Устанавливаем изображение для p1
                                                        p1.setImageBitmap(scaledBitmap);


                                                        // Устанавливаем правила размещения для p1 относительно новой ячейки
                                                        params.addRule(RelativeLayout.ALIGN_TOP, newCell.getId());
                                                        params.addRule(RelativeLayout.ALIGN_LEFT, newCell.getId());
                                                        p1.setVisibility(View.VISIBLE);
                                                        p1.setLayoutParams(params);
                                                        isPlayer1Visible = true;

                                                    }
                                                });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                    if(rnd==3){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Шанс").setMessage(shans[rnd]).setCancelable(false)
                                                .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                }
                                if(newPositionIndex==8){
                                    _tv_res.append(pl1+" отправляется в тюрьму "+ "\n");
                                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("Такова судьба").setMessage("Вы попадаете в тюрьму").setCancelable(false)
                                            .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    diceRoll=4;
                                                    newPositionIndex=4;
                                                    ImageButton newCell = cells[newPositionIndex];
                                                    // -1, так как индексы начинаются с 0

                                                    // Устанавливаем максимальные размеры изображения
                                                    int maxWidth = 70; // Ширина ячейки
                                                    int maxHeight = 70; // Высота ячейки
                                                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p1);

                                                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, maxWidth, maxHeight, true);


                                                    // Устанавливаем изображение для p1
                                                    p1.setImageBitmap(scaledBitmap);


                                                    // Устанавливаем правила размещения для p1 относительно новой ячейки
                                                    params.addRule(RelativeLayout.ALIGN_TOP, newCell.getId());
                                                    params.addRule(RelativeLayout.ALIGN_LEFT, newCell.getId());
                                                    p1.setVisibility(View.VISIBLE);
                                                    p1.setLayoutParams(params);
                                                    isPlayer1Visible = true;
                                                    _btn_Kybik.setEnabled(false);
                                                    Handler handler = new Handler();
                                                    long delayMillis = 5000;
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            _tv_res.append(pl1+" попал в тюрьму"+"\n");
                                                            int randomNumber2 = (int) (Math.random() * 6) + 1;
                                                            _pl2.setText(String.valueOf(randomNumber2));
                                                            diceRoll2 += randomNumber2;

                                                            if (diceRoll2 > 16) {
                                                                diceRoll2 -= 16;
                                                            }
                                                            //Toast.makeText(MainActivity.this, "Generated Number: " + randomNumber + "\nCurrent Sum: " + sum, Toast.LENGTH_SHORT).show();



                                                            if (b == 0) { // Первый бросок кубика
                                                                newPositionIndex2 = (diceRoll2) % cells.length;
                                                                b = 1;
                                                            } else { // Последующие броски кубика

                                                                newPositionIndex2 = (diceRoll2) % cells.length;
                                                                if (newPositionIndex2 >= 16 && player2 >= 16) {
                                                                    newPositionIndex2 = 0;
                                                                    player2 = 0;
                                                                }
                                                            }


                                                            ImageButton newCell2 = cells[newPositionIndex2];// -1, так как индексы начинаются с 0

                                                            // Устанавливаем максимальные размеры изображения
                                                            int maxWidth = 70; // Ширина ячейки
                                                            int maxHeight = 70; // Высота ячейки

                                                            Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);// Замените "your_image" на реальный идентификатор вашего изображения

                                                            Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, maxWidth, maxHeight, true);

                                                            // Устанавливаем изображение для p1

                                                            p2.setImageBitmap(scaledBitmap1);

                                                            // Устанавливаем правила размещения для p1 относительно новой ячейки

                                                            params2.addRule(RelativeLayout.ALIGN_TOP, newCell2.getId());
                                                            params2.addRule(RelativeLayout.ALIGN_RIGHT, newCell2.getId());

                                                            // Устанавливаем новые параметры для p1
                                                            _tv_res2.append("Игрок 2 походил на клетку "+ newPositionIndex2+ "\n");
                                                            p2.setLayoutParams(params2);
                                                            int rnd = (int) (Math.random() * 6) + 1;
                                                            player2 = diceRoll2;
                                                            if(newPositionIndex2<=2&& newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-1]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-1]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-1]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-1]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }


                                                            }
                                                            if(newPositionIndex2>=5 && newPositionIndex2<=7 && newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-3]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-3]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-3]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-3]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }

                                                            }
                                                            if(newPositionIndex2==14){
                                                                _tv_res2.append("Игрок 2 заплатил налог на роскошь "+ "\n");
                                                                dengi2-=75;
                                                                _tv_dengi2.setText(String.valueOf(dengi2));

                                                            }
                                                            if(newPositionIndex2>=9 && newPositionIndex2<=11&& newPositionIndex2!=0){
                                                                if(buy[newPositionIndex2-4]==0){
                                                                    if(rnd<=3){
                                                                        if(dengi2>200){
                                                                            buy[newPositionIndex2-4]=2;
                                                                            _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-4]+ "\n");
                                                                            dengi2-=100;
                                                                            _tv_dengi2.setText(String.valueOf(dengi2));
                                                                        }

                                                                    }
                                                                }
                                                                if(buy[newPositionIndex2-4]==1){
                                                                    _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                                    dengi2-=renta;
                                                                    dengi1+=renta;
                                                                    _tv_dengi2.setText(String.valueOf(dengi2));
                                                                    _tv_dengi1.setText(String.valueOf(dengi1));
                                                                }

                                                            }
                                                        }


                                                    }, delayMillis);

                                                    _btn_Kybik.setEnabled(true);
                                                }
                                            });
                                    AlertDialog alert=builder.create();
                                    alert.show();

                                }
                                if (newPositionIndex>=9 && newPositionIndex<=11&& newPositionIndex!=0){
                                    if(buy[newPositionIndex-4]==0){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Вы хотите купить?").setMessage("вы можете купить "+cities[newPositionIndex-4]+" за 100руб.").setCancelable(false)
                                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();




                                                    }
                                                });
                                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                buy[newPositionIndex-4]=1;
                                                Toast.makeText(MainActivity.this, "у вас списалось 100р.", Toast.LENGTH_SHORT).show();
                                                _tv_res.append(pl1+" купил "+cities[newPositionIndex-4]+ "\n");
                                                dengi1-=100;
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }
                                        });
                                        AlertDialog alert=builder.create();
                                        alert.show();
                                    }
                                    if(buy[newPositionIndex-4]==2){
                                        _tv_res.append(pl1+" заплатил ренту"+ "\n");
                                        dengi1-=50;
                                        dengi2+=50;
                                        _tv_dengi1.setText(String.valueOf(dengi1));
                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                    }
                                }

                                _pl2.setText(String.valueOf(randomNumber2));
                                Handler handler = new Handler();
                                long delayMillis = 5000;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        _btn_Kybik.setEnabled(true);
                                        params2.addRule(RelativeLayout.ALIGN_TOP, newCell2.getId());
                                        params2.addRule(RelativeLayout.ALIGN_RIGHT, newCell2.getId());
                                        p2.setLayoutParams(params2);
                                        int rnd = (int) (Math.random() * 6) + 1;
                                        if(newPositionIndex2<=2&& newPositionIndex2!=0){
                                            if(buy[newPositionIndex2-1]==0){
                                                if(rnd<=3){
                                                    if(dengi2>200){
                                                        buy[newPositionIndex2-1]=2;
                                                        _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-1]+ "\n");
                                                        dengi2-=100;
                                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                                    }

                                                }
                                            }
                                            if(buy[newPositionIndex2-1]==1){
                                                _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                dengi2-=renta;
                                                dengi1+=renta;
                                                _tv_dengi2.setText(String.valueOf(dengi2));
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }


                                        }
                                        if(newPositionIndex2>=5 && newPositionIndex2<=7 && newPositionIndex2!=0){
                                            if(buy[newPositionIndex2-3]==0){
                                                if(rnd<=3){
                                                    if (dengi2>200){
                                                        buy[newPositionIndex2-3]=2;
                                                        _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-3]+ "\n");
                                                        dengi2-=100;
                                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                                    }

                                                }
                                            }
                                            if(buy[newPositionIndex2-3]==1){
                                                _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                dengi2-=renta;
                                                dengi1+=renta;
                                                _tv_dengi2.setText(String.valueOf(dengi2));
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }

                                        }
                                        if(newPositionIndex2>=9 && newPositionIndex2<=11&& newPositionIndex2!=0){
                                            if(buy[newPositionIndex2-4]==0){
                                                if(rnd<=3){
                                                    if(dengi2>200){
                                                        buy[newPositionIndex2-4]=2;
                                                        _tv_res2.append("Игрок 2 купил "+cities[newPositionIndex2-4]+ "\n");
                                                        dengi2-=100;
                                                        _tv_dengi2.setText(String.valueOf(dengi2));
                                                    }

                                                }
                                            }
                                            if(buy[newPositionIndex2-4]==1){
                                                _tv_res2.append("Игрок 2 заплатил ренту "+ "\n");
                                                dengi2-=renta;
                                                dengi1+=renta;
                                                _tv_dengi2.setText(String.valueOf(dengi2));
                                                _tv_dengi1.setText(String.valueOf(dengi1));
                                            }

                                        }
                                        if(newPositionIndex2==14){
                                            _tv_res2.append("Игрок 2 заплатил налог на роскошь "+ "\n");
                                            dengi2-=75;
                                            _tv_dengi2.setText(String.valueOf(dengi2));

                                        }
                                        if(newPositionIndex2==4){
                                            _tv_res2.append("Игрок 2 попал в тюрьму"+ "\n");

                                            player2inJail=true;
                                            if(c==1){
                                                player2inJail=false;
                                            }


                                        }

                                        if(newPositionIndex2==3||newPositionIndex2==13||newPositionIndex2==15){
                                            _tv_res2.append("Игрок 2 взял шанс "+ "\n");
                                            int rnd2 = (int) (Math.random() * 3) + 1;
                                            if(rnd2==1){
                                                _tv_res2.append(shans[rnd2]+ "\n");
                                                dengi2-=100;
                                                _tv_dengi2.setText(String.valueOf(dengi2));
                                            }
                                            if(rnd2==2){
                                                _tv_res2.append(shans[rnd2]+ "\n");
                                                diceRoll2=0;
                                                newPositionIndex2=0;
                                                ImageButton newCell2 = cells[newPositionIndex2];// -1, так как индексы начинаются с 0

                                                // Устанавливаем максимальные размеры изображения
                                                int maxWidth = 70; // Ширина ячейки
                                                int maxHeight = 70; // Высота ячейки

                                                Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);// Замените "your_image" на реальный идентификатор вашего изображения

                                                Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, maxWidth, maxHeight, true);

                                                // Устанавливаем изображение для p1

                                                p2.setImageBitmap(scaledBitmap1);

                                                // Устанавливаем правила размещения для p1 относительно новой ячейки

                                                params2.addRule(RelativeLayout.ALIGN_TOP, newCell2.getId());
                                                params2.addRule(RelativeLayout.ALIGN_RIGHT, newCell2.getId());
                                                p2.setLayoutParams(params2);
                                            }
                                            if(rnd2==3){
                                                _tv_res2.append(shans[rnd2]+ "\n");
                                            }
                                        }

                                        if(newPositionIndex2==8){
                                            _tv_res2.append("Игрок 2 попал в тюрьму"+ "\n");
                                            newPositionIndex2=4;
                                            ImageButton newCell2 = cells[newPositionIndex2];// -1, так как индексы начинаются с 0

                                            // Устанавливаем максимальные размеры изображения
                                            int maxWidth = 70; // Ширина ячейки
                                            int maxHeight = 70; // Высота ячейки

                                            Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);// Замените "your_image" на реальный идентификатор вашего изображения

                                            Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, maxWidth, maxHeight, true);

                                            // Устанавливаем изображение для p1

                                            p2.setImageBitmap(scaledBitmap1);

                                            // Устанавливаем правила размещения для p1 относительно новой ячейки

                                            params2.addRule(RelativeLayout.ALIGN_TOP, newCell2.getId());
                                            params2.addRule(RelativeLayout.ALIGN_RIGHT, newCell2.getId());
                                            p2.setLayoutParams(params2);
                                            player2inJail=true;
                                            if(c==1){
                                                player2inJail=false;
                                            }


                                        }
                                        if(dengi1<=0 && dengi2<=0){
                                            resultat=3;
                                            Intent intent = new Intent(MainActivity.this, EndActivity.class);
                                            intent.putExtra("nichya", resultat);
                                            startActivity(intent);
                                        }
                                        if(dengi1<=0){
                                            resultat=2;
                                            Intent intent = new Intent(MainActivity.this, EndActivity.class);
                                            intent.putExtra("p1gameover", resultat);
                                            intent.putExtra("name",pl1);
                                            startActivity(intent);
                                        }
                                        if(dengi2<=0){
                                            resultat=1;
                                            Intent intent = new Intent(MainActivity.this, EndActivity.class);
                                            intent.putExtra("p2gameover", resultat);
                                            intent.putExtra("name",pl1);
                                            startActivity(intent);
                                        }


                                    }
                                }, delayMillis);

                            }


                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Ошибка").setMessage("Ваша фигура походила на другую клетку").setCancelable(false)
                                        .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert=builder.create();
                                alert.show();
                            }

                        }
                    });

                }

                _tv_res.append(pl1+" походил на клетку " +newPositionIndex+"\n");
                _tv_res2.append("Игрок 2 походил на клетку "+ newPositionIndex2+ "\n");



                Intent intent = new Intent(MainActivity.this, DiceActivity.class);
                intent.putExtra("diceRoll", randomNumber);
                startActivity(intent);


            }
        });




    }


}