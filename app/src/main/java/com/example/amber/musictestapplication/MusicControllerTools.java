package com.example.amber.musictestapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by gavin on 2017/7/31.
 */

public class MusicControllerTools {

    private long eventtime = SystemClock.uptimeMillis();
    private Context mContext;
    public static MusicControllerTools instance = null;
    private String ACTION_MY_BROAdCAST="com.action.my_broadcast";
    private MusicControllerTools(Context context) {
        this.mContext = context;
    }

    public static synchronized MusicControllerTools getInstance(Context context) {
        if (instance == null) {
            instance = new MusicControllerTools(context);
        }
        return instance;

    }

    //播放和暂停；
    public boolean controllerPlayAndPause(MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intentDown = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
            intentDown.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            mContext.sendOrderedBroadcast(intentDown, null);
            Intent intentUp = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
            intentUp.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            mContext.sendOrderedBroadcast(intentUp, null);
            Intent intent1=new Intent(ACTION_MY_BROAdCAST);
            mContext.sendBroadcast(intent1);
            return true;
        }
        return false;

    }

    //上一曲；
    public void controllerPrevious(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intentDown = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
            intentDown.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            mContext.sendOrderedBroadcast(intentDown, null);

            Intent intentUp = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
            intentUp.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            mContext.sendOrderedBroadcast(intentUp, null);

            Intent intent1=new Intent(ACTION_MY_BROAdCAST);
            mContext.sendBroadcast(intent1);
        }

    }

    //下一曲；
    public void controllerNext(MotionEvent motionEvent) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_MEDIA_NEXT, 0);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            mContext.sendOrderedBroadcast(intent, null);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_MEDIA_NEXT, 0);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            mContext.sendOrderedBroadcast(intent, null);
            Intent intent1=new Intent(ACTION_MY_BROAdCAST);
            mContext.sendBroadcast(intent1);
        }
    }

    //暂停；
    public void controllerStop(MotionEvent motionEvent) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_MEDIA_STOP, 0);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            mContext.sendOrderedBroadcast(intent, null);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_MEDIA_STOP, 0);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            mContext.sendOrderedBroadcast(intent, null);
            Intent intent1=new Intent(ACTION_MY_BROAdCAST);
            mContext.sendBroadcast(intent1);
        }
    }


/*    public void test() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);

        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        mContext.sendOrderedBroadcast(intent, null);

        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        mContext.sendOrderedBroadcast(intent, null);
    }*/

}
