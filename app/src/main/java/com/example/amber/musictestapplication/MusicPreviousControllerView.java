package com.example.amber.musictestapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by gavin on 2017/7/31.
 */

public class MusicPreviousControllerView extends android.support.v7.widget.AppCompatImageView {

    private Drawable mPreviousDra, mNotPreDra;
    private AudioManager mAudioManager;
    private Context mContext;
    MusicControllerTools mMusicControllerTools = MusicControllerTools.getInstance(getContext());

    public MusicPreviousControllerView(Context context) {
        this(context, null);
    }

    public MusicPreviousControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    public MusicPreviousControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (iPreviousListener != null) {
            iPreviousListener.previous();
        }
        if (mPreviousDra!=null)
            setImageDrawable(mPreviousDra);
        mMusicControllerTools.controllerPrevious(event);
//        setImageDrawable(mNotPreDra);
        return true;
    }

    private void initView(AttributeSet attrs) {
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }


    public void setPreviousDrawable(Drawable previous, Drawable previous_not,
                                    int width, int height,
                                    int left,int top,int right,int bottom) {
        getLayoutParams().height= height;
        getLayoutParams().width= width;
        LinearLayout.MarginLayoutParams marginLayoutParams =
                (LinearLayout.MarginLayoutParams) getLayoutParams();

//                new LinearLayout.MarginLayoutParams(layoutParams);
        marginLayoutParams.setMargins(left,top,right,bottom);
        setLayoutParams(marginLayoutParams);
        this.mPreviousDra = previous;
        this.mNotPreDra = previous_not;
        setImageDrawable(mPreviousDra);
    }

    IPreviousListener iPreviousListener;

    public void setPreviousListener(IPreviousListener listener) {
        this.iPreviousListener = listener;
    }

    public interface IPreviousListener {
        public void previous();
    }

}
