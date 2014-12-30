package com.example.steven.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnContinue = (Button)findViewById(R.id.BtnCont);
        Button btnAbout = (Button)findViewById(R.id.BtnAbout);
        Button btnNewGame = (Button)findViewById(R.id.BtnNewGame);
        Button btnExit = (Button)findViewById(R.id.BtnExit);

        final AboutActivity about = new AboutActivity(this);
        final DifficultyDialog dial = new DifficultyDialog(this);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about.show();

            }
        });
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial.show();
            }
        });

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
        switch (item.getItemId())
        {
            case R.id.action_settings :
                //getFragmentManager().beginTransaction().replace(android.R.id.content,new MyPrefActivity()).commit();
                Intent intent = new Intent(this,MyPrefActivity.class);
                startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Music.play(this,R.raw.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.stop(this);
    }
}
