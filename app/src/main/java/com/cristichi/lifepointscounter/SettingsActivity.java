package com.cristichi.lifepointscounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.cristichi.lifepointscounter.obj.Settings;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private EditText etLP;
    private EditText etPlayer1;
    private EditText etPlayer2;

    private SeekBar sbSound;
    private SeekBar sbMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sbSound = findViewById(R.id.seekBarSound);
        sbMusic = findViewById(R.id.seekBarMusic);
        etLP = findViewById(R.id.etLP);
        etPlayer1 = findViewById(R.id.etPlayer1);
        etPlayer2 = findViewById(R.id.etPlayer2);

        sbSound.setProgress((int)(Settings.current.soundVolume*10));
        sbMusic.setProgress((int)(Settings.current.musicVolume*10));
        etLP.setHint(String.valueOf(Settings.current.lp));
        etPlayer1.setHint(Settings.current.player1);
        etPlayer2.setHint(Settings.current.player2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String lp = etLP.getText().toString().trim();
        String p1 = etPlayer1.getText().toString().trim();
        String p2 = etPlayer2.getText().toString().trim();
        if (!lp.isEmpty())
            Settings.current.lp = Integer.parseInt(lp);
        if (!p1.isEmpty())
            Settings.current.player1 = p1;
        if (!p2.isEmpty())
            Settings.current.player2 = p2;

        Settings.current.soundVolume = ((float)(sbSound.getProgress())/10);
        Settings.current.musicVolume = ((float)(sbMusic.getProgress())/10);

        /* *
        File dir = getFilesDir();
        File file = new File(dir, Settings.FILE);

        try{
            FileOutputStream os = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(os);
            Settings.current.write(dos);
            dos.close();
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, R.string.settings_not_saved, Toast.LENGTH_SHORT).show();
        }
        /* */
        Settings.current.writeToFile();
    }
}
