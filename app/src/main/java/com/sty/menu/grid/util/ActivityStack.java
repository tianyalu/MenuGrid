package com.sty.menu.grid.util;

import android.app.Activity;
import android.util.Log;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by shity on 2017/8/31/0031.
 */

public class ActivityStack {
    private static final String TAG = ActivityStack.class.getSimpleName();

    private Deque<Activity> activities;
    private static ActivityStack instance;

    private ActivityStack(){
        activities = new LinkedList<>();
    }

    public static synchronized ActivityStack getInstance(){
        if(instance == null){
            instance = new ActivityStack();
        }

        return instance;
    }

    public void pop(){
        try{
            Activity activity = top();
            if(activity != null){
                remove(activity);
            }
        }catch (Exception e){
            Log.w(TAG, e);
        }
    }

    /**
     * 从栈的后面开始删除，直到删除自身界面为止
     * @param activity
     */
    public void popTo(Activity activity){
        if(activity != null){
            while(true){
                Activity lastCurrent = top();
                if(activity == top()){
                    return;
                }
                remove(lastCurrent);
            }
        }
    }

    public Activity top(){
        try{
            return activities.getLast();
        }catch (Exception e){
            Log.w(TAG, e);
        }
        return null;
    }

    public void push(Activity activity){
        activities.addLast(activity);
    }

    /**
     * c查找栈中是否存在指定的activity
     * @param cls
     * @return
     */
    public boolean find(Class<?> cls){
        for(Activity activity : activities){
            if(activity.getClass().equals(cls)){
                return true;
            }
        }
        return false;
    }

    /**
     * 除栈底外，其它的activity都pop掉
     */
    public void popAllButButtom(){
        while (true){
            Activity topActivity = top();
            if(topActivity == null || topActivity == bottom()){
                break;
            }
            remove(topActivity);
        }
    }

    /**
     * 结束栈中所有activity
     */
    public void popAll(){
        if(activities == null){
            return;
        }
        while(true){
            Activity activity = top();
            if(activity == null){
                break;
            }
            remove(activity);
        }
    }

    public Activity bottom(){
        return activities.getFirst();
    }

    private void remove(Activity activity){
        activity.finish();
        activities.remove(activity);
    }
}
