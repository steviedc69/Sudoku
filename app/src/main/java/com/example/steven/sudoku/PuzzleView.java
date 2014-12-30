package com.example.steven.sudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;


/**
 * TODO: document your custom view class.
 */
public class PuzzleView extends View {

    private static final String TAG = "Sudoku.view";

    //private final Game game;

    private int tileWidth;
    private int tileHeight;
    private int cursorX;
    private int cursorY;

    private Paint background;
    private Paint hilite;
    private Paint light;
    private Paint dark;
    private Paint foreground;
    private Paint selected;

    private final Rect selRect = new Rect();

      private Game game;

    public PuzzleView(Context context) {
        super(context);
        this.game = (Game)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        init();
    }

    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.game = (Game)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        init();
    }

    public PuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.game = (Game)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        init();
    }

    public void init() {
        //background = new paint
        Log.d(TAG,"Init");
        this.background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));

        this.dark = new Paint();
        this.dark.setColor(getResources().getColor(R.color.puzzle_dark));

        this.foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.foreground.setColor(getResources().getColor(R.color.puzzle_foreground));

        this.hilite = new Paint();
        this.hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

        this.light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));

        this.selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));

        this.requestFocus();
        this.setFocusableInTouchMode(true);

    }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"widthMeasureSpec : "+widthMeasureSpec+" , heightMeasureSpec : "+heightMeasureSpec);
        Log.d(TAG,"MeasureWidth : "+measureWidth(widthMeasureSpec)+" , MeasureHeight : "+measureHeight(heightMeasureSpec));
        //use rect cursorX * measureHight/tileheight idem y

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        getRect((int)(cursorX * height/tileWidth),(int)(cursorY * width/tileHeight) ,selRect);
        setMeasuredDimension(width,height);
    }

    private void getRect(int x, int y, Rect rect){
        rect.set((int) (x * tileWidth), (int) (y * tileHeight),
                (int) (x * tileWidth + tileWidth), (int) (y * tileHeight + tileHeight));
    }

    private int measureHeight(int measureSpec){
        //getMode
        int measureMode = MeasureSpec.getMode(measureSpec);
        //getsize
        int measureSize = MeasureSpec.getSize(measureSpec);
        //unspecified == 500
        int result;
        if(measureMode == MeasureSpec.UNSPECIFIED)
        {

            result = 500;
        }
        else
        {

            result = measureSize;
        }
        //tileHeight / 9
        tileHeight = result/9;
        return result;
    }

    private int measureWidth(int measureSpec){
        //getMode
        int measureMode = MeasureSpec.getMode(measureSpec);
        //getsize
        int measureSize = MeasureSpec.getSize(measureSpec);
        //unspecified == 500
        int result;
        if(measureMode == MeasureSpec.UNSPECIFIED)
        {
            result = 500;
        }
        else
        {
            result = measureSize;
        }
        //tileHeight / 9
        tileWidth = result/9;
        Log.d(TAG,"TileWidth : "+tileWidth);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {



            //canvas.drawRect(0,0,getWidth(),getHeight(),background)
            Log.d(TAG, "OnDraw");
            canvas.drawRect(0, 0, getWidth(), getHeight(), background);

            for (int i = 0; i < 9; i ++) {
                canvas.drawLine(0, i * tileHeight, getWidth(), i * tileHeight, light);
                canvas.drawLine(0, i * tileHeight+1, getWidth(), i * tileHeight+1, hilite);

                canvas.drawLine(i * tileWidth,0,i * tileWidth,getHeight(), light);
                canvas.drawLine(i * tileWidth+1,0,i * tileWidth+1,getHeight(), hilite);

                canvas.drawRect(0, getWidth(), 0, getHeight(), background);
            }

        for (int i = 0; i < 9 ; i++)
        {
            if(i%3 != 0)
                continue;
            canvas.drawLine(0,i*tileHeight,getWidth(),i*tileHeight,dark);
            canvas.drawLine(0,i*tileHeight+1,getWidth(),i*tileHeight+1,hilite);

            canvas.drawLine(i*tileWidth,0,i*tileWidth,getHeight(),dark);
            canvas.drawLine(i*tileWidth+1,0,i*tileWidth+1,getHeight(),hilite);
        }


        Paint.FontMetrics fm = foreground.getFontMetrics();
        foreground.setTextSize(tileHeight * 0.6f);
        foreground.setTextScaleX(tileWidth / tileHeight);
        float x = tileWidth / 2;
        float y = tileHeight / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getTileString(i, j), i * tileWidth + x, j * tileHeight + y, foreground);
            }
        }

        if(MyPrefActivity.getHints(game)) {


            Paint hint = new Paint();
            int c[] = {getResources().getColor(R.color.puzzle_hint_0), getResources().getColor(R.color.puzzle_hint_1), getResources().getColor(R.color.puzzle_hint_2)};

            Rect r = new Rect();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int movesLeft = 9 - game.getUsedTiles(i, j).length;
                    if (movesLeft < c.length) {
                        getRect(i, j, r);
                        hint.setColor(c[movesLeft]);
                        canvas.drawRect(r, hint);
                    }
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //TODO: vul de codes in bij passende key down events, om de cursor te verplaatsen
        Log.d(TAG,"OnKeyDown, keyCode : "+keyCode+", event : "+event);

        switch (keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_UP :
                select(cursorX,cursorY-1);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN :
                select(cursorX,cursorY+1);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT :
                select(cursorX-1,cursorY);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT :
                select(cursorX+1,cursorY);
                break;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                setSelectedTile(0);
                break;
            case KeyEvent.KEYCODE_1:
                setSelectedTile(1);
                break;
            case KeyEvent.KEYCODE_2:
                setSelectedTile(2);
                break;
            case KeyEvent.KEYCODE_3:
                setSelectedTile(3);
                break;
            case KeyEvent.KEYCODE_4:
                setSelectedTile(4);
                break;
            case KeyEvent.KEYCODE_5:
                setSelectedTile(5);
                break;
            case KeyEvent.KEYCODE_6:
                setSelectedTile(6);
                break;
            case KeyEvent.KEYCODE_7:
                setSelectedTile(7);
                break;
            case KeyEvent.KEYCODE_8:
                setSelectedTile(8);
                break;
            case KeyEvent.KEYCODE_9:
                setSelectedTile(9);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                game.showKeypadOrError(cursorX, cursorY);
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public void setSelectedTile(int tile) {
       if (game.setTileIfValid(cursorX, cursorY, tile)){
            invalidate();// may change hints
        } else {
            // Number is not valid for this tile
            Log.d(TAG, "setSelectedTile: invalid: " + tile);
           startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
        }
    }

    private void select(int x, int y){
        //Is heel belangrijk!
        //TODO vul de code aan om te nieuwe x en y coordinaten te
        //bepalen om de nieuw geselecteerde rechthoek te bepalen
        // GEBRUIK INVALIDATE
        invalidate(selRect);
        cursorX = Math.min(Math.max(x,0),8);
        cursorY = Math.min(Math.max(y,0),8);
        getRect(cursorX,cursorY,selRect);
        invalidate(selRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //TODO: te implementeren
        if(event.getAction() != MotionEvent.ACTION_DOWN)
        {
            return super.onTouchEvent(event);
        }
        select((int)(event.getX()/tileWidth),(int)(event.getY()/tileHeight));
        game.showKeypadOrError(cursorX,cursorY);

        return true;
    }
}
