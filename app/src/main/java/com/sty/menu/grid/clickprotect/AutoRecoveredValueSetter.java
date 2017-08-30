package com.sty.menu.grid.clickprotect;

import android.os.SystemClock;

/**
 * 快速点击保护，注要用在界面功能选择
 * Created by shity on 2017/8/30/0030.
 */

public class AutoRecoveredValueSetter<T> {
    T value;
    T recoveredTo;
    long timeoutMs;

    protected void setValue(T value){
        this.value = value;
    }

    protected T getValue(){
        return value;
    }

    protected void setRecoverTo(T value){
        this.recoveredTo = recoveredTo;
    }

    protected void setTimeoutMs(long timeoutMs){
        this.timeoutMs = timeoutMs;
    }

    protected void recover(){
        this.value = recoveredTo;
    }

    protected void autoRecover(){
       MyApplication.getApp().runInBackground(new Runnable() {
           @Override
           public void run() {
               SystemClock.sleep(timeoutMs);
               setValue(recoveredTo);
           }
       });
    }
}
