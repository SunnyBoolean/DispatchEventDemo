package com.geo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by liwei on 2017/6/6.
 */

public class MyLinearLayout extends LinearLayout {
    final String LOG_TAG="EVENT_LOG";
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 如果返回true，则说明事件由它自己处理了，false则表示它没有处理，要分发下去了
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(LOG_TAG,"容器：dispatchTouchEvent()-->返回");
        boolean result = super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        if(action==MotionEvent.ACTION_MOVE){
            Log.e(LOG_TAG,"MOVE");
        }else if(action==MotionEvent.ACTION_DOWN){
            Log.e(LOG_TAG,"DOWN");
        }else if(action==MotionEvent.ACTION_UP){
            Log.e(LOG_TAG,"UP");
        }else if(action==MotionEvent.ACTION_CANCEL){
            Log.e(LOG_TAG,"CANCEL");
        }
        result = true;
        return result;
    }

    /**
     * 如果为true，就说明它要拦截这个事件自己消费掉，否则就不拦截了
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(LOG_TAG,"容器：onInterceptTouchEvent()-->返回");
        boolean result = super.onInterceptTouchEvent(ev);
        result=false;
        return true;
    }

    /**
     * 如果事件被处理了就返回true，没处理就返回false
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(LOG_TAG,"容器：onTouchEvent()-->返回");
        boolean result = super.onTouchEvent(event);
        result = true;
        return result;
    }
}
