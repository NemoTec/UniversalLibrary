
package com.nemo.ul.util;

import android.os.Looper;






public class ULLooperUtil {
	
	/**
     * @return ��ǰ�Ƿ�Ϊ���߳�
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}

	