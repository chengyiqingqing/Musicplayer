package com.example.amber.musictestapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by gavin on 2017/7/31.
 */

public class MusicControllerView extends LinearLayout {

    private MusicPlayControllerView mPlayAndPauseImageView;
    private MusicNextControllerView mNextImageView;
    private MusicPreviousControllerView mPreviousImageView;
    private MusicName mMusicName;
    AudioManager mAudioManager;
    MediaController mMediaController;
    private long eventtime = SystemClock.uptimeMillis();
    Handler mHandler = new Handler();
    public MusicControllerView(Context context) {
        this(context,null);
    }

    public MusicControllerView(Context context,  AttributeSet attrs) {
        super(context, attrs);

        View view=LayoutInflater.from(context).inflate(R.layout.music_controller, this);
        setOrientation(HORIZONTAL);
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mPreviousImageView = (MusicPreviousControllerView)view.findViewById(R.id.musicPreviousControllerView);
        mPlayAndPauseImageView = (MusicPlayControllerView)view.findViewById(R.id.musicPlayControllerView);
        mNextImageView = (MusicNextControllerView)view.findViewById(R.id.musicNextControllerView);
//        mMusicName = (MusicName) findViewById(R.id.music_name);
        Log.d("DEMO","PLAY"+ mPlayAndPauseImageView);
        initView(attrs);
        mNextImageView.setNextlistener(new MusicNextControllerView.INextListener() {
            @Override
            public void next() {
                if (mAudioManager.isMusicActive()) {

                } else {
//                    mPlayAndPauseImageView.setImageNextAndPrevious();
                }
            }
        });

        mPreviousImageView.setPreviousListener(new MusicPreviousControllerView.IPreviousListener() {
            @Override
            public void previous() {
                if (mAudioManager.isMusicActive()) {

                } else {
//                    mPlayAndPauseImageView.setImageNextAndPrevious();
                }
            }
        });
    }

    public void initView (AttributeSet attrs) {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MusicControllerView);
        Drawable playDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_playImage);
        Drawable pauseDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_pauseImage);
        Drawable previous_notDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_previous_notImage);
        Drawable previousDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_previousImage);
        Drawable next_notDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_nextnotImage);
        Drawable nextDrawable = typedArray.getDrawable(R.styleable.MusicControllerView_nextImage);
        int margin_top = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_margin_top,0);
        int margin_left = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_margin_left,0);
        int margin_right = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_margin_right,0);
        int margin_Bottom = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_margin_bottom,0);

        int image_width = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_image_width,0);
        if (image_width == 0){
            image_width = -2;
        }

        int image_height = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_image_height,0);
        if (image_height == 0){
            image_height = -2;
        }

        int text_size = typedArray.getDimensionPixelSize(R.styleable.MusicControllerView_text_size,90);
        int text_color = typedArray.getColor(R.styleable.MusicControllerView_text_color,Color.BLACK);

//        MarginLayoutParams marginPlayLayoutParams = new MarginLayoutParams(
//                new LayoutParams(image_width,image_height));
//        marginPlayLayoutParams.setMargins(margin_left,margin_top,margin_right,margin_Bottom);

//        MarginLayoutParams marginNextLayoutParams = new MarginLayoutParams(
//                new ViewGroup.LayoutParams(image_width,image_height));
//        marginNextLayoutParams.setMargins(margin_left,margin_top,margin_right,margin_Bottom);

//        MarginLayoutParams marginPreviousLayoutParams = new MarginLayoutParams(
//                new ViewGroup.LayoutParams(image_width,image_height));
//        marginPreviousLayoutParams.setMargins(margin_left,margin_top,margin_right,margin_Bottom);

//        mPlayAndPauseImageView.setLayoutParams(marginPlayLayoutParams);
//        mPreviousImageView.setLayoutParams(marginPreviousLayoutParams);
//        mNextImageView.setLayoutParams(marginNextLayoutParams);

        mPlayAndPauseImageView.setPlayPauseDrawable(playDrawable,pauseDrawable,
                image_width,image_height,
                margin_left,margin_top,margin_right,margin_Bottom
        );

        mPreviousImageView.setPreviousDrawable(previousDrawable, previous_notDrawable,
                image_width,image_height,
                margin_left,margin_top,margin_right,margin_Bottom);

        Log.d("DEMO",margin_left+" "+margin_top);

        mNextImageView.setNextDrawable(nextDrawable, next_notDrawable,
                image_width,image_height,
                margin_left,margin_top,margin_right,margin_Bottom);

//        mMusicName.setTextStatus(text_size,text_color);
//        mPlayAndPauseImageView.setPlayPauseDrawable(play_drawable,pause_drawable);
//        mPreviousImageView.setPreviousDrawable(mPreviousDra, mNotPreDra);
//        mNextImageView.setNextDrawable(mStopDra, mNotStopDra);
        typedArray.recycle();
    }

    boolean isTrue=true;

    public void changeButton() {
         mPlayAndPauseImageView.changeIcon();
    }

}
