package com.example.myitogproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class MainActivitybase extends AppCompatActivity {
    TextView _tv_tv;
    TableLayout _tblay;

    private static final String FILENAME = "test.txt";
    private HashSet<String> playerNamesSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitybase);
        playerNamesSet = new HashSet<>();
        _tv_tv=(TextView)findViewById(R.id.tv_tv);
        _tblay=(TableLayout) findViewById(R.id.tblay);
        int p1win = getIntent().getIntExtra("win2", 0);
        String p1 = getIntent().getStringExtra("name2");
        write(p1,p1win);
        read();




    }
    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            String lines;
            while ((lines = reader.readLine()) != null) {
                String[] parts = lines.split(",");
                if (parts.length >= 2) {
                    String playerName = parts[0];
                    int game = Integer.parseInt(parts[1]);
                    if (playerNamesSet.contains(playerName)) {
                        // Обновляем результат игрока
                        updatePlayerScore(playerName, game);
                    } else {
                        // Добавляем нового игрока
                        addNewPlayer(playerName, game);
                        playerNamesSet.add(playerName);
                    }
                } else {
                    // Обработка ситуации, когда строка не содержит двух элементов после разделения
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePlayerScore(String playerName, int game) {
        for (int i = 0; i < _tblay.getChildCount(); i++) {
            View view = _tblay.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                TextView nameTextView = (TextView) row.getChildAt(0);
                if (nameTextView.getText().toString().equals(playerName)) {
                    TextView gameTextView = (TextView) row.getChildAt(1);
                    int currentGame = Integer.parseInt(gameTextView.getText().toString());
                    gameTextView.setText(String.valueOf(currentGame + 1)); // Увеличиваем результат на 1
                    break; // Выходим из цикла после обновления результата
                }
            }
        }
    }

    private void addNewPlayer(String playerName, int game) {
        TableRow row = new TableRow(this);
        TextView nameTextView = new TextView(this);
        nameTextView.setText(playerName);
        row.addView(nameTextView);
        TextView gameTextView = new TextView(this);
        gameTextView.setText(String.valueOf(game));
        row.addView(gameTextView);
        _tblay.addView(row);
    }

    public void write(String p1, int game){

        try {
            FileOutputStream fileOutput = openFileOutput(FILENAME,MODE_APPEND);
            String data =p1+","+String.valueOf(game)+"\n";
            fileOutput.write(data.getBytes());
            fileOutput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}