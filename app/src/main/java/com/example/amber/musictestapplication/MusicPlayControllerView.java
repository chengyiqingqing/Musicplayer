package com.example.amber.musictestapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gavin on 2017/7/31.
 */

public class MusicPlayControllerView extends android.support.v7.widget.AppCompatImageView {

    public Drawable play_drawable, pause_drawable;
    public AudioManager mAudioManager;
    boolean isPlay, PlayStatus;
    boolean isSetPlay=false,isSetPause=false;
    private Timer timer;
    private TimerTask task;
    public final int WHAT=100;
    MusicControllerTools mMusicControllerTools = MusicControllerTools.getInstance(getContext());

    public MusicPlayControllerView(Context context) {
        this(context, null);
    }

    public MusicPlayControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MusicPlayControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        final IntentFilter filter = new IntentFilter();
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                Log.e(TAG, "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.e(TAG, "screen on");
                    setTimeTask();
                }
                if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.e(TAG, "screen off");
                    if (timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                    if (task!=null){
                        task.cancel();
                        task=null;
                    }
                }


            }
        };
        Log.d(TAG, "registerReceiver");
        context.registerReceiver(mInfoReceiver, filter);
        initView(attrs);
        setTimeTask();
    }

    /**
     * 初始化布局；
     * attrs;
     * @param attrs
     */
    public void initView(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MusicPlayControllerView);
        Drawable playDrawable = typedArray.getDrawable(R.styleable.MusicPlayControllerView_music_playImage);
        Drawable pauseDrawable = typedArray.getDrawable(R.styleable.MusicPlayControllerView_music_pauseImage);
        this.play_drawable=playDrawable;
        this.pause_drawable=pauseDrawable;

    }

    public void setTimeTask(){
        Log.e(TAG, " getFocus , setTimeTask" );
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
        if (task!=null){
            task.cancel();
            task=null;
        }
        timer=new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = WHAT;
                message.obj = System.currentTimeMillis();
                handler.sendMessage(message);
                Log.e(TAG, "checking " );
            }
        };
        // 参数：
        // 0，延时1秒后执行。
        // 2000，每隔2秒执行1次task。
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus){
            //如果焦点获得，进行操作
            Log.e(TAG, "onWindowFocusChanged: 获取焦点" );
            setTimeTask();
        }else{
            if (timer!=null){
                timer.cancel();
                timer=null;
            }
            if (task!=null){
                task.cancel();
                task=null;
            }
            Log.e(TAG, "onWindowFocusChanged: 失去焦点" );

        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMusicControllerTools.controllerPlayAndPause(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setImage();
        }

        return true;
    }

    public void setPlayPauseDrawable(Drawable play,Drawable pause,
                                     int width, int height,
                                     int left,int top,int right,int bottom) {

        getLayoutParams().height= height;
        getLayoutParams().width= width;
        LinearLayout.MarginLayoutParams marginLayoutParams =
                (LinearLayout.MarginLayoutParams) getLayoutParams();

        marginLayoutParams.setMargins(left,top,right,bottom);
        setLayoutParams(marginLayoutParams);
        this.play_drawable = play;
        this.pause_drawable = pause;
//
//        if (mAudioManager.isMusicActive()) {
//            setImageDrawable(pause_drawable);
//            isPlay = true;
//        } else {
//            setImageDrawable(play_drawable);
//            isPlay = false;
//        }
    }

    public void setImage() {
        if (mAudioManager.isMusicActive()) {
            setImageDrawable(play_drawable);
        } else {
            setImageDrawable(pause_drawable);
        }
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT:
                    changeIcon();
                    break;
            }
        }
    };
    private static final String TAG = "MusicPlayControllerView";
    public void changeIcon() {

        if (mAudioManager.isMusicActive()) {//该设置pause了--》是否已经设置了pause；没有则设置pause;有则不进行设置；
//            Log.e(TAG, "changeIcon: if 11111");
            if (!isSetPause){//没有进行设置；
                Log.e(TAG, "changeIcon:if -> if 22222");
                setImageDrawable(pause_drawable);
                isSetPause=true;
                isSetPlay=false;//修改；
                Intent intent1=new Intent("com.action.my_broadcast");
                getContext().sendBroadcast(intent1);
            }else{//进行设置了，那么不进行修改；
//                Log.e(TAG, "changeIcon:if -> else 22222");
            }
        } else {
//            Log.e(TAG, "changeIcon: else -> 11111");
            if (!isSetPlay){//没有设置过play状态
                setImageDrawable(play_drawable);
                isSetPlay=true;
                isSetPause=false;
                Intent intent1=new Intent("com.action.my_broadcast");
                getContext().sendBroadcast(intent1);
                Log.e(TAG, "changeIcon: else -> if 22222");
            }else{//已进行设置；
//                Log.e(TAG, "changeIcon: else -> else 22222");

            }
        }
    }

    public void setImageNextAndPrevious() {
        setImageDrawable(pause_drawable);
        isPlay = true;
    }

}
