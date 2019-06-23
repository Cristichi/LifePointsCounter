package com.cristichi.lifepointscounter;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cristichi.lifepointscounter.components.VerticalTextView;
import com.cristichi.lifepointscounter.obj.Settings;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LifeCounterActivity extends AppCompatActivity {

    protected int LP1;
    protected int LP2;

    protected TextView tvLP1;
    protected TextView tvLP2;
    protected ProgressBar pbPlayer1;
    protected ProgressBar pbPlayer2;
    protected LinearLayout llPlayer1;
    protected LinearLayout llPlayer2;

    @SuppressWarnings("All")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);
        LayoutInflater inflater = (LayoutInflater) getBaseContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView tv1Player1 = findViewById(R.id.tv1Player1);
        TextView tv2Player1 = findViewById(R.id.tv2Player1);
        TextView tv1Player2 = findViewById(R.id.tv1Player2);
        TextView tv2Player2 = findViewById(R.id.tv2Player2);
        tvLP1 = findViewById(R.id.tvLP1);
        tvLP2 = findViewById(R.id.tvLP2);
        pbPlayer1 = findViewById(R.id.progressBarPlayer1);
        pbPlayer2 = findViewById(R.id.progressBarPlayer2);
        llPlayer1 = findViewById(R.id.llPlayer1);
        llPlayer2 = findViewById(R.id.llPlayer2);

        try{
            Bundle extras = getIntent().getExtras();
            String strLP = extras.getString("lp");
            int intLP = -1;
            try{
                intLP = Integer.parseInt(strLP);
            }catch (NumberFormatException e){
                Toast.makeText(this, R.string.main_error_LP, Toast.LENGTH_SHORT).show();
                throw e;
            }
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
            LP1 = LP2 = intLP;

            List<List<Integer>> buttons = Settings.current.buttons;

            //Buttons Player 1
            for (List<Integer> list : buttons){
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.counter_buttons_column, null);
                for (Integer integer : list){
                    VerticalTextView tvButton = (VerticalTextView) inflater.inflate(R.layout.counter_buttons_button_270, null);
                    tvButton.setTag(integer);
                    tvButton.setText(String.valueOf(integer>0?"+"+integer:integer));
                    tvButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addLP1((Integer)v.getTag());
                        }
                    });
                    linearLayout.addView(tvButton);
                }
                llPlayer1.addView(linearLayout);
            }

            //Buttons Player 2
            Collections.reverse(buttons);
            for (List<Integer> list : buttons){
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.counter_buttons_column, null);
                for (Integer integer : list){
                    VerticalTextView tvButton = (VerticalTextView) inflater.inflate(R.layout.counter_buttons_button_90, null);
                    tvButton.setTag(integer);
                    tvButton.setText(String.valueOf(integer > 0 ? "+" + integer : integer));
                    tvButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addLP2((Integer)v.getTag());
                        }
                    });
                    linearLayout.addView(tvButton);
                }
                llPlayer2.addView(linearLayout);
            }

        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    protected void addLP1(int adding){
        LP1 += adding;
        if (LP1<0)
            LP1=0;
        pbPlayer1.setProgress(LP1);
        tvLP1.setText(String.valueOf(LP1));
    }

    protected void addLP2(int adding){
        LP2 += adding;
        if (LP2<0)
            LP2=0;
        pbPlayer2.setProgress(LP2);
        tvLP2.setText(String.valueOf(LP2));
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
