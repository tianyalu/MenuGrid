package com.sty.menu.grid.clickprotect;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class QuickClickProtection extends AQuickClickProtection {
    private static QuickClickProtection quickClickProtection;

    private QuickClickProtection(long timeoutMs){
        super(timeoutMs);
    }

    public static synchronized QuickClickProtection getInstance(){
        if(quickClickProtection == null){
            quickClickProtection = new QuickClickProtection(500);
        }

        return quickClickProtection;
    }
}
