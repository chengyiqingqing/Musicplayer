package com.example.amber.musictestapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gavin on 2017/8/4.
 */

public class MusicName extends android.support.v7.widget.AppCompatTextView {

    public final int WHAT=100;
    String mCurPlayerPackageName;
    private static final String TAG = "MusicName";

    public MusicName(Context context) {
        this(context,null);
    }

    public MusicName(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MusicName(Context context, AttributeSet attrs) {
        super(context, attrs);

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
            String track = intent.getStringExtra("track");
            String albumName = intent.getStringExtra("album");
            String artist = intent.getStringExtra("artist");
            //=============

            Long music_id = intent.getLongExtra("id", 0);
            String artistName = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            boolean playing = intent.getBooleanExtra("playing", false);
            Long duration = intent.getLongExtra("duration", 3000);
            Long position = intent.getLongExtra("position", 1000);
            Log.e(TAG, "onReceive: "+"music_id:"+music_id+"  playing:"+ playing + " duration:"+ duration +" position:" + position);
            //=============
            setText(track + " ---- " + artist);
            /*String action = intent.getAction();

            if ("com.kugou.android".equals(mCurPlayerPackageName)) {//酷狗音乐
                if ("com.kugou.android.music.playstatechanged".equals(action)) {
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");
                } else if ("com.kugou.android.music.metachanged".equals(action)) {
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");
                }
            } else if ("com.maxmpz.audioplayer".equals(mCurPlayerPackageName)) {//PowerAMP
                if ("com.maxmpz.audioplayer.STATUS_CHANGED".equals(action)) {
                        Bundle track_bundle = intent.getBundleExtra("track");
                        if (track_bundle != null) {
                            track = track_bundle.getString("track");
                            albumName = track_bundle.getString("album");
                            artist = track_bundle.getString("artist");
                        }
                    }
            } else if ("com.maxmpz.audioplayer.TRACK_CHANGED".equals(action)) {
                        Bundle track_bundle = intent.getBundleExtra("track");
                        if (track_bundle != null) {
                            track = track_bundle.getString("track");
                            albumName = track_bundle.getString("album");
                            artist = track_bundle.getString("artist");
                        }
                }
            else {
                if ("com.android.music.playstatechanged".equals(action) || (mCurPlayerPackageName + ".playstatechanged").equals(action) || (mCurPlayerPackageName + ".music.playstatechanged").equals(action)) {
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");

                } else if ("com.android.music.metachanged".equals(action)) {
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");
                } else if((mCurPlayerPackageName + ".metachanged").equals(action) || (mCurPlayerPackageName + ".music.metachanged").equals(action)) {
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");
                }else{
                    track = intent.getStringExtra("track");
                    albumName = intent.getStringExtra("album");
                    artist = intent.getStringExtra("artist");
                }
            }*/
            /*if (!track.equals("")||!track.equals(null))
                setText(""+track + " ---- " + artist);*/

            //=============================================

        }

    };

    public void setTextStatus(int textSize,int textColor){
        setTextSize(textSize);
        setTextColor(textColor);
    }

}
