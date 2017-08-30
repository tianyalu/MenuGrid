package com.sty.menu.grid.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class AlwaysMarqueeTextView extends TextView {

    public AlwaysMarqueeTextView(Context context){
        super(context);
        setAlwaysMarquee();
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        setAlwaysMarquee();
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        setAlwaysMarquee();
    }

    private void setAlwaysMarquee(){
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setSingleLine();
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
