package com.example.amber.musictestapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by Amber on 2017/9/7.
 */

public class MusicNameShowRelative extends RelativeLayout {
    private static final String TAG = "MusicNameShowRelative";
    private MusicDefaultImageView defaultMusicName;
    private Drawable music_Image;
    private TextView musicName;

    public final int WHAT=100;
    String mCurPlayerPackageName;
    private String ACTION_MY_BROAdCAST="com.action.my_broadcast";

    public MusicNameShowRelative(Context context) {
        this(context,null);
    }
    public MusicNameShowRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context,attrs);
    }

    public MusicNameShowRelative(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view= LayoutInflater.from(context).inflate(R.layout.rl_musicname, this);
        defaultMusicName= (MusicDefaultImageView) view.findViewById(R.id.default_music_image);
        musicName= (TextView) view.findViewById(R.id.music_name);
       /* LayoutParams lp1 = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        this.addView(defaultMusicName,lp1);
        this.addView(musicName,lp1);*/

        initView(attrs);
        defaultMusicName.setImageDrawable(music_Image);
        musicName.setText("歌名显示");
        musicName.setBackgroundColor(Color.parseColor("#33000000"));
        IntentFilter intentFilter=new IntentFilter(ACTION_MY_BROAdCAST);
        context.registerReceiver(new MyBroadCastReceiver(),intentFilter);
    }
    public void initView(AttributeSet attrs){

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MusicNameShowRelative);
        music_Image = typedArray.getDrawable(R.styleable.MusicNameShowRelative_music_Image_default);
        int image_margin_top = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_margin_top,0);
        int image_margin_left = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_margin_left,0);
        int image_margin_right = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_margin_right,0);
        int image_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_margin_bottom,0);
        int image_width = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_width_setting,0);
        if (image_width == 0){
            image_width = 40;
        }

        int image_height = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_image_height_setting,0);
        Log.e(TAG, "initView: "+image_height );
        if (image_height == 0){
            image_height = 40;
        }

//        String music_TextView = typedArray.getString(R.styleable.MusicNameShowRelative_music_TextView);


        int text_margin_top = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_margin_top,0);
        int text_margin_left = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_margin_left,0);
        int text_margin_right = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_margin_right,0);
        int text_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_margin_bottom,0);

//        int text_width = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_width_setting,0);


        int text_height = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_height_setting,0);
        if (text_height == 0){
            text_height = 40;
        }

        int text_size = typedArray.getDimensionPixelSize(R.styleable.MusicNameShowRelative_text_size_setting,90);
        int text_color = typedArray.getColor(R.styleable.MusicNameShowRelative_text_color_setting, Color.BLACK);
        setTextView(text_height,text_margin_left,text_margin_top,text_margin_right,text_margin_bottom);
        defaultMusicName.setImageView(music_Image,image_width,image_height,image_margin_left,image_margin_top,image_margin_right,image_margin_bottom);
        setGravity(CENTER_HORIZONTAL);
        registerBC();
    }


    public void setTextView( int height, int left, int top, int right, int bottom){
        musicName.getLayoutParams().height=height;
        RelativeLayout.MarginLayoutParams marginLayoutParams= (RelativeLayout.MarginLayoutParams) musicName.getLayoutParams();
        marginLayoutParams.setMargins(left,top,right,bottom);

    }

    public void registerBC(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.music.metachanged");
        intentFilter.addAction("com.android.music.playstatechanged");
        intentFilter.addAction("com.android.music.playbackcomplete");
        intentFilter.addAction("com.android.music.queuechanged");
        intentFilter.addAction("com.htc.music.metachanged");
        intentFilter.addAction("fm.last.android.metachanged");
        intentFilter.addAction("com.sec.android.app.music.metachanged");
        intentFilter.addAction("com.nullsoft.winamp.metachanged");
        intentFilter.addAction("com.amazon.mp3.metachanged");
        intentFilter.addAction("com.miui.player.metachanged");
        intentFilter.addAction("com.real.IMP.metachanged");
        intentFilter.addAction("com.sonyericsson.music.metachanged");
        intentFilter.addAction("com.rdio.android.metachanged");
        intentFilter.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        intentFilter.addAction("com.andrew.apollo.metachanged");

        intentFilter.addAction("com.andrew.apollo.metachanged");

        intentFilter.addAction("com.ting.mp3.playinfo_changed");
        intentFilter.addAction("com.maxmpz.audioplayer.STATUS_CHANGED");
        intentFilter.addAction("com.maxmpz.audioplayer.TRACK_CHANGED");
        intentFilter.addAction("com.kugou.android.music.metachanged");
        intentFilter.addAction("com.kugou.android.music.playstatechanged");
        intentFilter.addAction("com.kuwo.android.music.metachanged");
        intentFilter.addAction("com.kuwo.android.music.playstatechanged");
        intentFilter.addAction("com.coolme.android.music.metachanged");
        intentFilter.addAction("com.coolme.android.music.playstatechanged");


        intentFilter.addAction("com.test.broadcast.standard");
        intentFilter.addAction("com.test.broadcast.local");


        mCurPlayerPackageName = MusicControlUtils.getCurMediaPlayerComponentName(getContext()).getPackageName();
        Log.e(TAG, "MusicName: "+ mCurPlayerPackageName);
        if (!TextUtils.isEmpty(mCurPlayerPackageName)) {
            intentFilter.addAction(mCurPlayerPackageName + ".metachanged");
//		        commandFilter.addAction(mCurPlayerPackageName + ".queuechanged");
//		        commandFilter.addAction(mCurPlayerPackageName + ".playbackcomplete");
            intentFilter.addAction(mCurPlayerPackageName + ".playstatechanged");

            intentFilter.addAction(mCurPlayerPackageName + ".music.metachanged");
//		        commandFilter.addAction(mCurPlayerPackageName + ".music.queuechanged");
//		        commandFilter.addAction(mCurPlayerPackageName + ".music.playbackcomplete");
            intentFilter.addAction(mCurPlayerPackageName + ".music.playstatechanged");
        }
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String track = intent.getStringExtra("track");
            String albumName = intent.getStringExtra("album");
            final String artist = intent.getStringExtra("artist");
            //=============

            Long music_id = intent.getLongExtra("id", 0);
            String artistName = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            boolean playing = intent.getBooleanExtra("playing", false);
            Long duration = intent.getLongExtra("duration", 3000);
            Long position = intent.getLongExtra("position", 1000);
            Log.e(TAG, "onReceive: "+"music_id:"+music_id+"  playing:"+ playing + " duration:"+ duration +" position:" + position);
            //=============

            new Handler().postDelayed(new Runnable(){
                public void run() {
                    //execute the task
                    musicName.setText(track + " ---- " + artist);
                    musicName.setVisibility(View.VISIBLE);
                    defaultMusicName.setVisibility(View.INVISIBLE);
                }
            }, 1500);


            //=============================================

        }

    };

    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: mybroadcastreceiver" );
            defaultMusicName.setVisibility(View.VISIBLE);
            musicName.setVisibility(View.INVISIBLE);
        }

    }


}
