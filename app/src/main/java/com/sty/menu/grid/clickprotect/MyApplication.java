package com.sty.menu.grid.clickprotect;

import android.app.Application;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
    private Handler handler;
    private ExecutorService backgroundExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        com.wanjian.sak.LayoutManager.init(this); //使用UI调试插件

        MyApplication.mApp = this;
        handler = new Handler();
        backgroundExecutor = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Background executor service");
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    public static MyApplication getApp(){
        return mApp;
    }

    public void runInBackground(final Runnable runnable){
        backgroundExecutor.submit(runnable);
    }

    public void runOnUiThread(final Runnable runnable){
        handler.post(runnable);
    }

    public void runOnUiThreadDelay(final Runnable runnable, long delayMillis){
        handler.postDelayed(runnable, delayMillis);
    }
}
