package com.sty.menu.grid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class CustomGridView extends GridView {
    public CustomGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public CustomGridView(Context context){
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //int heightSpec = MeasureSpec.makeMeasureSpec(900, MeasureSpec.AT_MOST);
//        for(int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            Log.i("Tag", "child count:-->" + getChildCount() + "\nchild Tag:-->" + child.getTag().toString() +
//                    "\nchild:-->" + child.toString() + "" + "\nchild height:-->" + child.getMeasuredHeight());
//        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
