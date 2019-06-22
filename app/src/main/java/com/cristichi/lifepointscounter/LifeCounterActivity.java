package com.cristichi.lifepointscounter;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LifeCounterActivity extends AppCompatActivity {

    private TextView tv1Player1;
    private TextView tv2Player1;
    private TextView tv1Player2;
    private TextView tv2Player2;
    private TextView tvLP1;
    private TextView tvLP2;
    private ProgressBar pbPlayer1;
    private ProgressBar pbPlayer2;

    @SuppressWarnings("All")
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

    protected void addLP1(int adding){
        int lp = pbPlayer1.getProgress()+adding;
        pbPlayer1.setProgress(lp);
        tvLP1.setText(String.valueOf(lp));
    }

    protected void addLP2(int adding){
        int lp = pbPlayer2.getProgress()+adding;
        pbPlayer2.setProgress(lp);
        tvLP2.setText(String.valueOf(lp));
    }

    protected void subLP1(int substracting){
        int lp = pbPlayer1.getProgress()-substracting;
        if (lp<0)
            lp=0;
        pbPlayer1.setProgress(lp);
        tvLP1.setText(String.valueOf(lp));
    }

    protected void subLP2(int substracting){
        int lp = pbPlayer2.getProgress()-substracting;
        if (lp<0)
            lp=0;
        pbPlayer2.setProgress(lp);
        tvLP2.setText(String.valueOf(lp));
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
