package com.example.steven.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Steven on 14/10/14.
 */
public class Music {

    private static MediaPlayer mp = null;

    public static void play(Context context, int res)
    {
        stop(context);

        if(MyPrefActivity.getMusic(context))
        {
            mp = MediaPlayer.create(context,res);
            mp.setLooping(true);
            mp.start();
        }

    }
    public static void stop(Context context)
    {
        if(mp != null)
        {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
