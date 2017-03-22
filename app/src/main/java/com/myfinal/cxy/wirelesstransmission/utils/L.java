package com.myfinal.cxy.wirelesstransmission.utils;


import android.util.Log;

import java.util.Iterator;


/**
 * Created by CXY on 2016/11/8.
 */

public class L<T> {
    private static boolean isDebug = true;
    private static String TAG = "CXY";
    private static final String CLASS_NAME= L.class.getName();

    public synchronized static void e(Object[] msg) {
        if (!isDebug) return;
        StringBuffer sb = new StringBuffer();
        sb.append("------------------------------------------------------------------------\n");
        sb.append(getTargetStackTraceElement().toString() + "\n");
        for(Object o : msg){
            sb.append(o+"\n");
        }
        sb.append("------------------------------------------------------------------------\n");
        print(sb);
    }
    public synchronized static <T> void e(T msg) {
        if (!isDebug) return;
        StringBuffer sb = new StringBuffer();
        sb.append("------------------------------------------------------------------------");
        sb.append("\n" + getTargetStackTraceElement().toString() + "\n");
        if (msg instanceof Number || msg instanceof Boolean || msg instanceof String) {
            sb.append(String.valueOf(msg)+"\n");
        } else if (msg instanceof Iterable) {
            Iterator iterator = ((Iterable) msg).iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next() +"\n");
            }
        }
        sb.append("------------------------------------------------------------------------\n");
        print(sb);
    }

    private static void print(StringBuffer sb){
        Log.e(TAG,sb.toString());
    }

    private static StackTraceElement getTargetStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(CLASS_NAME);
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
