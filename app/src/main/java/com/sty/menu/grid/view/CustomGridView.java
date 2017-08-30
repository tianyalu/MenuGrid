package com.sty.menu.grid.view;

import android.content.Context;
import android.util.AttributeSet;
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

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
