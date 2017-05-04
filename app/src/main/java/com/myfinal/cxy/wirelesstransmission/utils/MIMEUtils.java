package com.myfinal.cxy.wirelesstransmission.utils;

import com.myfinal.cxy.wirelesstransmission.bean.FileItemInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxy on 17-3-15.
 */

public class MIMEUtils {

    public static final Map<String, Integer> map = new LinkedHashMap<String, Integer>() {
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
            put("XHTML", FileItemInfo.Type.html);

            put("RC", FileItemInfo.Type.sys_file);
            put("SH", FileItemInfo.Type.sys_file);
            put("PROP", FileItemInfo.Type.sys_file);

            put("PDF", FileItemInfo.Type.pdf);

        }
    };
    //根据后缀获取文件类型的图标
    public static int get(String key) {
        Integer integer = map.get(key);
        return integer != null ? integer.intValue() : FileItemInfo.Type.unknow;
    }

    private static final Map<String, String> mimeTypeMap = new HashMap<String, String>();

    static {
        add("application/ogg", "ogg");
        add("application/pdf", "pdf");
        add("application/rar", "rar");
        add("application/zip", "zip");
        add("application/vnd.android.package-archive", "apk");
        add("application/msword", "doc");
        add("application/msword", "dot");
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.template", "dotx");
        add("application/vnd.ms-excel", "xls");
        add("application/vnd.ms-excel", "xlt");
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.template", "xltx");
        add("application/vnd.ms-powerpoint", "ppt");
        add("application/vnd.ms-powerpoint", "pot");
        add("application/vnd.ms-powerpoint", "pps");
        add("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        add("application/vnd.openxmlformats-officedocument.presentationml.template", "potx");
        add("application/vnd.openxmlformats-officedocument.presentationml.slideshow", "ppsx");
        add("application/x-shockwave-flash", "swf");
        add("application/xhtml+xml", "xhtml");
        add("audio/3gpp", "3gpp");
        add("audio/mpeg", "mpga");
        add("audio/mpeg", "mpega");
        add("audio/mpeg", "mp3");
        add("audio/mpegurl", "m3u");
        add("image/bmp", "bmp");
        add("image/gif", "gif");
        add("image/ico", "cur");
        add("image/ico", "ico");
        add("image/jpeg", "jpeg");
        add("image/jpeg", "jpg");
        add("image/jpeg", "jpe");
        add("image/pcx", "pcx");
        add("image/png", "png");
        add("image/svg+xml", "svg");
        add("image/svg+xml", "svgz");


        add("text/css", "css");
        add("text/html", "htm");
        add("text/html", "html");

        add("text/plain", "txt");

        add("text/rtf", "rtf");
        add("text/xml", "xml");

        add("text/x-java", "java");

        add("video/m4v", "m4v");
        add("video/mpeg", "mpeg");
        add("video/mpeg", "mpg");
        add("video/mpeg", "mpe");
        add("video/mp4", "mp4");
        add("video/mpeg", "VOB");
    }

    private static void add(String mimeType, String extension) {
        if (!mimeTypeMap.containsKey(mimeType)) {
            mimeTypeMap.put(mimeType, extension);
        }
    }


    public static boolean hasMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return false;
        }
        return mimeTypeMap.containsKey(mimeType);
    }

    public static String getMimeType(String value) {
        if(value!=null && !value.equals("")){
            for (Map.Entry<String,String> entry : mimeTypeMap.entrySet()){
                if(entry.getValue().equals(value)){
                    return entry.getKey();
                }
            }
        }
        return "text/plain";
    }

    public static void main(String[] s){
        MIMEUtils.hasMimeType("txt");
    }
}
