package com.geo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by liwei on 2017/6/6.
 */

public class MyButton extends Button{
    final String LOG_TAG="EVENT_LOG";
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(LOG_TAG,"Button：dispatchTouchEvent()-->返回");
        boolean result = super.dispatchTouchEvent(ev);
        return result;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(LOG_TAG,"Button：onTouchEvent()-->返回");
        boolean result = super.onTouchEvent(event);
        return result;
    }
}
