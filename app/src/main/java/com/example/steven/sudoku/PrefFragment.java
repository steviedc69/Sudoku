package com.example.steven.sudoku;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Steven on 9/10/14.
 */
public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.setting);
    }
}
