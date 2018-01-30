package com.woodblockwithoutco.remotecontrollerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Amber on 2017/9/7.
 */

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "testBroadCast";
    String ACTION_STANDARD="com.test.broadcast.standard";
    String ACTION_LOCAL="com.test.broadcast.local";
    TestBroadCast testBroadCastStandard;
    TestBroadCast testBroadCastLocal;
    LocalBroadcastManager localmanager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localmanager=LocalBroadcastManager.getInstance(this);
        testBroadCastStandard=new TestBroadCast();
        testBroadCastLocal=new TestBroadCast();
        registerStandard();
        registerLocal();
    }

    public void registerStandard(){
        IntentFilter intentFilter=new IntentFilter(ACTION_STANDARD);
        registerReceiver(testBroadCastStandard,intentFilter);
    }

    public void registerLocal(){
        IntentFilter intentFilter=new IntentFilter(ACTION_LOCAL);
        localmanager.registerReceiver(testBroadCastLocal,intentFilter);
    }

    public void sendStandard(View view){
        Intent intent=new Intent(ACTION_STANDARD);
        sendBroadcast(intent);
    }

    public void sendLocal(View view){
        Intent intent=new Intent(ACTION_LOCAL);
        localmanager.sendBroadcast(intent);
    }

    class TestBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_STANDARD))
                Log.e(TAG, "onReceive: standard" );
            else if (intent.getAction().equals(ACTION_LOCAL))
                Log.e(TAG, "onReceive: order" );
            else
                Log.e(TAG, "onReceive: unknown " );
        }

    }


}
