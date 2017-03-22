package com.myfinal.cxy.wirelesstransmission.utils;

import android.content.Context;
import android.widget.Toast;
/*****************************************
 *
 *@author cxy
 *created at  2016/10/18 11:15
 *
 ****************************************/
public class T {
    public static <T> void show(Context context, T msg) {
        Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_SHORT).show();
    }
}
