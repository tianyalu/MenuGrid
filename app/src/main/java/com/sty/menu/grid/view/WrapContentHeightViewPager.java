package com.sty.menu.grid.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shity on 2017/8/30/0030.
 * for ViewPager WrapContent
 * from http://blog.csdn.net/xushichao08/article/details/54020403
 */

public class WrapContentHeightViewPager extends ViewPager {
    private float mPointX;
    private float mPointY;

    /**
     * Constructor
     * @param context
     */
    public WrapContentHeightViewPager(Context context){
        super(context);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public WrapContentHeightViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * 自定义ViewPager, 在onMeasure方法中计算它子View的高度，取最高的来作为自己的高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for(int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height){
                height = h;
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    /**
     * 解决GridView 与 ViewPager嵌套后滑动不灵敏的问题
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPointX = ev.getX();
                mPointY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(Math.abs(ev.getX() - mPointX) >
                    Math.abs(ev.getY() - mPointY));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
