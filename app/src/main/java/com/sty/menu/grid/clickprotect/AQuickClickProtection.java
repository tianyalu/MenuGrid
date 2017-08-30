package com.sty.menu.grid.clickprotect;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class AQuickClickProtection extends AutoRecoveredValueSetter<Boolean> {
    public AQuickClickProtection(long timeoutMs){
        setTimeoutMs(timeoutMs);
        setValue(false);
        setRecoverTo(false);
    }

    /**
     * 默认500ms
     */
    public AQuickClickProtection(){
        setTimeoutMs(500);
        setValue(false);
        setRecoverTo(false);
    }

    public boolean isStarted(){
        return getValue();
    }

    public void start(){
        setValue(true);
        autoRecover();
    }

    public void stop(){
        recover();
    }
}
