package com.example.amber.musictestapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Amber on 2017/9/7.
 */

public class MusicDefaultImageView  extends android.support.v7.widget.AppCompatImageView{
    private Drawable imageview;
    public MusicDefaultImageView(Context context) {
        this(context,null);
    }

    public MusicDefaultImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MusicDefaultImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {

    }

    public void setImageView(Drawable default_imageview,
                             int width, int height,
                             int left,int top,int right,int bottom) {

        getLayoutParams().height= height;
        getLayoutParams().width= width;

        RelativeLayout.MarginLayoutParams marginLayoutParams =
                (RelativeLayout.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.setMargins(left,top,right,bottom);
        setLayoutParams(marginLayoutParams);
        imageview=default_imageview;
        setImageDrawable(default_imageview);
    }

}
