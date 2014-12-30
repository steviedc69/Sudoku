package com.example.steven.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Steven on 2/10/14.
 */
public class DifficultyDialog extends Dialog {

    private ListView list;

    public DifficultyDialog(Context context) {
        super(context);

        final Context c = context;
        Resources res = context.getResources();
        String[]difficulties = res.getStringArray(R.array.difficulties);
        ArrayList<String> difList = new ArrayList<String>(Arrays.asList(difficulties));


        this.setContentView(R.layout.difficulty_layout);
        list = (ListView)findViewById(R.id.dialogList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,difList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent gameIntent = new Intent(c,Game.class);
                Log.d(Game.KEY_DIFFICULTY,"difficulty : "+i);
                    gameIntent.putExtra(Game.KEY_DIFFICULTY,i);
                  c.startActivity(gameIntent);
            }
        });

        this.setCancelable(true);
        this.setTitle(R.string.difficulty);
    }


}
