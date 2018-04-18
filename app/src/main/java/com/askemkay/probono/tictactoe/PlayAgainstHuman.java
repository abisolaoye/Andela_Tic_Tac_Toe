package com.askemkay.probono.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class PlayAgainstHuman extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;

    private int roundCount;

    private int playerOnePoints;
    private int playerTwoPoints;

    private TextView playerOne;
    private TextView playerTwo;
    private SharedPreferences preferences;
    private String preferencesKey = "TicTacToe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_against_human);

        playerOne = findViewById(R.id.p1_textView);
        playerTwo = findViewById(R.id.p2_textView);

        preferences = this.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);

        if (preferences.contains("PlayerOne"))
            playerOnePoints = preferences.getInt("PlayerOne", 0);

        if (preferences.contains("PlayerTwo"))
            playerTwoPoints = preferences.getInt("PlayerTwo", 0);

        playerOne.setText("Player 1: " + playerOnePoints);
        playerTwo.setText("Player 2: " + playerTwoPoints);


        getSupportActionBar();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ID = "button" + i + j;

                int resId = getResources().getIdentifier(ID, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = findViewById(R.id.buttonReset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (playerOneTurn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }

        roundCount++;

        if (checkWin()) {
            if (playerOneTurn) {
                playerOneWins();
            } else {
                playerTwoWins();
            }
        } else if (roundCount == 9) {
            draw();
        }
        else {
            playerOneTurn = !playerOneTurn;
        }
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")) {
                return true;
            }
        }

        return false;
    }

    private void playerOneWins() {
        playerOnePoints++;

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("PlayerOne", playerOnePoints).apply();

        Toast.makeText(this, "Player One has won", Toast.LENGTH_SHORT).show();
        playerOne.setText("Player 1: " + playerOnePoints);
        resetBoard();

    }

    private void playerTwoWins() {
        playerTwoPoints++;

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("PlayerTwo", playerTwoPoints).apply();

        Toast.makeText(this, "Player Two has won", Toast.LENGTH_SHORT).show();
        playerTwo.setText("Player 2: " + playerTwoPoints);
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "A blasted draw has ensued...-_-", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }

        }

        playerOneTurn = true;
        roundCount = 0;
    }

    private void resetGame(){
        playerTwoPoints = 0;
        playerOnePoints = 0;

        playerOne.setText("Player 1: " + playerOnePoints);
        playerTwo.setText("Player 2: " + playerTwoPoints);

        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOne", playerOnePoints);
        outState.putInt("playerTwo", playerTwoPoints);
        outState.putBoolean("Turn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOne");
        playerTwoPoints = savedInstanceState.getInt("playerTwo");
        playerOneTurn = savedInstanceState.getBoolean("Turn");
    }
}
