package com.example.amber.musictestapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by gavin on 2017/7/31.
 */

public class MusicNextControllerView extends android.support.v7.widget.AppCompatImageView {

    public Drawable mNextDra, mNotNextDra;
    public AudioManager mAudioManager;
    public MusicControllerTools mMusicControllerTools = MusicControllerTools.getInstance(getContext());

    public MusicNextControllerView(Context context) {
        this(context, null);
    }

    public MusicNextControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MusicNextControllerView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
       /* setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicControllerTools.getInstance(context).test();
            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (iNextListener != null) {
            iNextListener.next();
        }

        mMusicControllerTools.controllerNext(event);
        if (mNextDra!=null)
            setImageDrawable(mNextDra);
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
        this.mNextDra = next;
        this.mNotNextDra = next_not;
        setImageDrawable(mNextDra);
    }

    INextListener iNextListener;

    public void setNextlistener(INextListener listener) {
        this.iNextListener = listener;
    }

    public interface INextListener {
        public void next();
    }

}
