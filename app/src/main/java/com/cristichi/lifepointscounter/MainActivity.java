package com.cristichi.lifepointscounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.cristichi.lifepointscounter.obj.Settings;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText etLP;
    private EditText etName1;
    private EditText etName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File dir = getFilesDir();
        File file = new File(dir, Settings.FILE);

        if (file.exists())
            try{
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                if (!Settings.current.read(dis)){
                    Toast.makeText(this, R.string.settings_not_saved, Toast.LENGTH_SHORT).show();
                }
                dis.close();
            }catch (IOException e){
                Toast.makeText(this, R.string.settings_not_saved, Toast.LENGTH_SHORT).show();
            }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        etLP = findViewById(R.id.etLP);
        etName1 = findViewById(R.id.etPlayer1Name);
        etName2 = findViewById(R.id.etPlayer2Name);

        if (Settings.current.player1.isEmpty()){
            Settings.current.player1 = etName1.getHint().toString();
        }if (Settings.current.player2.isEmpty()){
            Settings.current.player2 = etName2.getHint().toString();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Zona en obras", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                String lp, name1, name2;
                lp = etLP.getText().toString().trim();
                lp = (lp.isEmpty()?etLP.getHint().toString(): lp);

                name1 = etName1.getText().toString();
                name1 = (name1.isEmpty()?etName1.getHint().toString(): name1);

                name2 = etName2.getText().toString();
                name2 = (name2.isEmpty()?etName2.getHint().toString(): name2);

                Intent intent = new Intent(MainActivity.this, LifeCounterActivity.class);
                intent.putExtra("lp", lp);
                intent.putExtra("Player1", name1.trim());
                intent.putExtra("Player2", name2.trim());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        etLP.setHint(String.valueOf(Settings.current.lp));
        if (!Settings.current.player1.isEmpty())
            etName1.setHint(Settings.current.player1);
        if (!Settings.current.player2.isEmpty())
            etName2.setHint(Settings.current.player2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_coin) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
