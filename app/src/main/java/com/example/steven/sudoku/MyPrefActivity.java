package com.example.steven.sudoku;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Steven on 2/10/14.
 */
public class MyPrefActivity extends PreferenceActivity{

    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;
    private static final String OPT_HINTS = "hints";
    private static final boolean OPT_HINTS_DEF = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PrefFragment()).commit();


    }

    public static boolean getMusic(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_MUSIC,OPT_MUSIC_DEF);
    }

    public static boolean getHints(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_HINTS,OPT_HINTS_DEF);
    }


}
