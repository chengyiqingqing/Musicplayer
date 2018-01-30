package com.example.amber.musictestapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class MusicStopControllerView extends android.support.v7.widget.AppCompatImageView {

    public Drawable mStopDra, mNotStopDra;
    public AudioManager mAudioManager;
    public MusicControllerTools mMusicControllerTools = MusicControllerTools.getInstance(getContext());

    public MusicStopControllerView(Context context) {
        this(context, null);
    }

    public MusicStopControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MusicStopControllerView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (iStopListener != null) {
            iStopListener.next();
        }

        mMusicControllerTools.controllerStop(event);
        if (mStopDra !=null)
            setImageDrawable(mStopDra);
        return true;
    }

    public void initView (AttributeSet attrs) {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void setNextDrawable(Drawable next, Drawable next_not,
                                int width, int height,
                                int left,int top,int right,int bottom) {
        Log.d("LOG"," "+top);
        getLayoutParams().height= height;
        getLayoutParams().width= width;
        LinearLayout.MarginLayoutParams marginLayoutParams =
                (LinearLayout.MarginLayoutParams) getLayoutParams();

//                new LinearLayout.MarginLayoutParams(layoutParams);
        marginLayoutParams.setMargins(left,top,right,bottom);

        setLayoutParams(marginLayoutParams);
        this.mStopDra = next;
        this.mNotStopDra = next_not;
        setImageDrawable(mStopDra);
    }

    IStopListener iStopListener;

    public void setNextlistener(IStopListener listener) {
        this.iStopListener = listener;
    }

    public interface IStopListener {
        public void next();
    }

}
