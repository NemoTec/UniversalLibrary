
package com.nemo.ul.util;

import android.os.Looper;






public class ULLooperUtil {
	
	/**
     * @return 当前是否为主线程
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}

	