package com.example.clockapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Amber on 2017/9/18.
 */

public class ClockView extends RelativeLayout{

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

    }

}
