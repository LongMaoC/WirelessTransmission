package com.myfinal.cxy.wirelesstransmission.utils;

import android.content.Intent;

import com.myfinal.cxy.wirelesstransmission.bean.FileItemInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxy on 17-3-15.
 */

public class MIMEUtils {
    public static final Map<String ,Integer> map = new LinkedHashMap<String ,Integer>(){
        {
            put("3GP", FileItemInfo.Type.movie);
            put("AVI", FileItemInfo.Type.movie);
            put("FLV", FileItemInfo.Type.movie);
            put("MP4", FileItemInfo.Type.movie);
            put("MPEG", FileItemInfo.Type.movie);
            put("RMVB", FileItemInfo.Type.movie);

            put("AMR", FileItemInfo.Type.movie);
            put("WAV", FileItemInfo.Type.music);
            put("MP3", FileItemInfo.Type.music);
            put("WMA", FileItemInfo.Type.music);
            put("OGG", FileItemInfo.Type.music);
            put("APE", FileItemInfo.Type.music);
            put("MPEG", FileItemInfo.Type.music);

            put("BMP", FileItemInfo.Type.image);
            put("PNG", FileItemInfo.Type.image);
            put("JPEG", FileItemInfo.Type.image);
            put("GIF", FileItemInfo.Type.image);
            put("MPEG", FileItemInfo.Type.image);

            put("XML", FileItemInfo.Type.xml);

            put("RAR", FileItemInfo.Type.rar);
            put("ZIP", FileItemInfo.Type.rar);
            put("7Z", FileItemInfo.Type.rar);

            put("APK", FileItemInfo.Type.apk);

            put("DOC", FileItemInfo.Type.office);
            put("XLS", FileItemInfo.Type.office);
            put("PPT", FileItemInfo.Type.office);
            put("DOCX", FileItemInfo.Type.office);
            put("XLSX", FileItemInfo.Type.office);
            put("PPTX", FileItemInfo.Type.office);

            put("JAR", FileItemInfo.Type.jar);

            put("HTM", FileItemInfo.Type.html);
            put("HTML", FileItemInfo.Type.html);

            put("RC", FileItemInfo.Type.sys_file);
            put("SH", FileItemInfo.Type.sys_file);
            put("PROP", FileItemInfo.Type.sys_file);

            put("PDF", FileItemInfo.Type.pdf);

        }
    };

    public static int get(String key){
        Integer integer = map.get(key);
        return integer!=null ? integer.intValue():FileItemInfo.Type.unknow ;
    }
}
