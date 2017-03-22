package com.myfinal.cxy.wirelesstransmission.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Looper;

/**
 * Created by cxy on 17-3-16.
 */

public class ClipboardUtils {
    private static ClipboardUtils instance;
    private static  ClipboardManager cm;
    private Context context;
    private ClipboardUtils(Context context) {
        this.context = context.getApplicationContext();
         cm = (ClipboardManager)this.context.getSystemService(Context.CLIPBOARD_SERVICE);

    }
    public static ClipboardUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ClipboardUtils(context);
        }
        return instance;
    }
    public String getClipboardContent(){
        ClipData.Item itemAt = cm.getPrimaryClip().getItemAt(0);
        if(itemAt==null)return "" ;
        String s = itemAt.getText().toString();
        return s ;
    }

    public void  setClipboardContent(String content){
        cm.setPrimaryClip(ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, content));
    }
}
