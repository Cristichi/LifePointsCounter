package com.cristichi.lifepointscounter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LifeCounterActivity extends AppCompatActivity {

    TextView tv1Player1;
    TextView tv2Player1;
    TextView tv1Player2;
    TextView tv2Player2;
    TextView tvLP1;
    TextView tvLP2;
    ProgressBar pbPlayer1;
    ProgressBar pbPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);

        tv1Player1 = findViewById(R.id.tv1Player1);
        tv2Player1 = findViewById(R.id.tv2Player1);
        tv1Player2 = findViewById(R.id.tv1Player2);
        tv2Player2 = findViewById(R.id.tv2Player2);
        tvLP1 = findViewById(R.id.tvLP1);
        tvLP2 = findViewById(R.id.tvLP2);
        pbPlayer1 = findViewById(R.id.progressBarPlayer1);
        pbPlayer2 = findViewById(R.id.progressBarPlayer2);

        try{
            Bundle extras = getIntent().getExtras();
            String strLP = extras.getString("LP");
            int intLP = Integer.parseInt(strLP);
            String player1 = extras.getString("Player1");
            String player2 = extras.getString("Player2");

            tv1Player1.setText(player1);
            tv2Player1.setText(player1);
            tv1Player2.setText(player2);
            tv2Player2.setText(player2);

            tvLP1.setText(strLP);
            tvLP2.setText(strLP);

            pbPlayer1.setMax(intLP);
            pbPlayer2.setMax(intLP);
            pbPlayer1.setProgress(intLP);
            pbPlayer2.setProgress(intLP);


        }catch (Exception e){
            Log.e("CRISTICHIEX", "ERROR: "+e.toString());
            Log.e("CRISTICHIEX", "ERROR: "+e.toString());
            Log.e("CRISTICHIEX", "ERROR: "+e.toString());
            e.printStackTrace();
            finish();
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getText(R.string.press_back_again), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
