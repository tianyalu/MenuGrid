package com.sty.menu.grid.view;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = ViewPagerAdapter.class.getSimpleName();
    private List<View> lists;

    public ViewPagerAdapter(List<View> data){
        lists = data;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try{
            //解决View只能滑动两屏的方法
            ViewGroup parent = (ViewGroup) lists.get(position).getParent();
            if(parent != null){
                parent.removeView(lists.get(position));
            }

            if(container != null){
                container.addView(lists.get(position), 0);
            }
        }catch (Exception e){
            Log.e(TAG, "", e);
        }
        return lists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try{
            container.removeView(lists.get(position));
        }catch (Exception e){
            Log.e(TAG, "", e);
        }
    }
}
