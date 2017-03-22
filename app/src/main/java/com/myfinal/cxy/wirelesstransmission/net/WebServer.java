package com.myfinal.cxy.wirelesstransmission.net;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myfinal.cxy.wirelesstransmission.MyApplication;
import com.myfinal.cxy.wirelesstransmission.bean.FileItemInfo;
import com.myfinal.cxy.wirelesstransmission.bean.FileItemInfoBean;
import com.myfinal.cxy.wirelesstransmission.utils.ClipboardUtils;
import com.myfinal.cxy.wirelesstransmission.utils.FileUtils;
import com.myfinal.cxy.wirelesstransmission.utils.L;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CXY on 2017/1/10.
 */
public class WebServer extends NanoHTTPD {
    public interface Conf{
        int prot = 8080;
         Map<String, String> extensions = new HashMap<String, String>() {
            {
                put("htm", "text/html");
                put("html", "text/html");
                put("xml", "text/xml");
                put("txt", "text/plain");
                put("json", "text/plain");
                put("css", "text/css");
                put("ico", "image/x-icon");
                put("png", "image/png");
                put("gif", "image/gif");
                put("jpg", "image/jpg");
                put("jpeg", "image/jpeg");
                put("zip", "application/zip");
                put("rar", "application/rar");
                put("js", "text/javascript");
            }
        };

        String uri_pull_w2a="/myfinal_pull_text_function_w2a";
        String uri_pull_w2a_get="/myfinal_pull_text_function_w2a_get";
        String uri_push_w2a="/myfinal_push_text_function_w2a";
        
        String uri_up_file_name="/up_file_name";
        String uri_del_dir = "/uri_del_dir";
        String uri_get_project_catch_dir = "/uri_get_project_catch_dir";
        String uri_get_file_catch_dir = "/uri_get_file_catch_dir";
        String uri_phone_info="/uri_phone_info";
        String uri_down_load = "/uri_down_load";
        String uri_upload_file = "/uri_upload_file";




        String key_push_text = "push_text";
        String key_path = "path";
        String key_fileName = "fileName";
        String key_file_type= "file_type";
        String key_download_file_path= "key_download_file_path";
        String key_upload_name = "name";

        String flag_wx = "wx";
        String flag_qq = "qq";



    }
    private static Gson gson;

    public WebServer() {
        super(Conf.prot);
        gson = new Gson();
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header,
                          Map<String, String> parameters,
                          Map<String, String> files) {

        StringBuffer sb = new StringBuffer();
        sb.append("Method = "+method.toString()+"\turi = "+uri);
        sb.append("\nparameters = "+parameters.toString());
        sb.append("\nfiles = "+files.toString());
        L.e(sb.toString());


        if(method.equals(Method.GET)){
            if (uri.equals("/")) {
                return new Response(Response.Status.OK, "text/html", new String(FileUtils.readAssets("index.html")));
            }else if (uri.equals("/index2.html")) {
                return new Response(Response.Status.OK, "text/html", new String(FileUtils.readAssets("index2.html")));
            }else {
                // 获取文件类型
                String type = WebServer.Conf.extensions.get(uri.substring(uri.lastIndexOf(".") + 1));
                if (TextUtils.isEmpty(type))
                    return new Response("");
                // 读取文件
                byte[] b = FileUtils.readAssets(uri.substring(1));
                if (b == null || b.length < 1)
                    return new Response("");
                // 响应
                return new Response(Response.Status.OK, type, new ByteArrayInputStream(b));
            }
        }else{
            if(files.size()>0){

                if(Conf.uri_upload_file.equals(uri)){

                    // 读取文件
                    for (String s : files.keySet()) {
                        FileInputStream fis =null;
                        FileOutputStream fos = null ;
                        try {
                             fis = new FileInputStream(files.get(s));
                            File outputFile = FileUtils.createOutFile(parameters.get(Conf.key_upload_name));
                            if(outputFile==null && outputFile.equals(""))return null ;
                            L.e("out : "+outputFile.getAbsolutePath());
                             fos = new FileOutputStream(outputFile);

                            byte[] buffer = new byte[1024];
                            while (true) {
                                int byteRead = fis.read(buffer);
                                if (byteRead == -1) {
                                    break;
                                }
                                fos.write(buffer, 0, byteRead);
                            }
                            fos.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                fis.close();
                                fos.close();
                            }catch (Exception e){}
                        }
                    }

                }

            }else{

                if(Conf.uri_pull_w2a.equals(uri)){//拉取剪切板内容请求
                    try {
                        str="";
                        GetClipboardThread thread = new GetClipboardThread();
                        thread.start();
                        new Response(Response.Status.OK, "application/json", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(Conf.uri_pull_w2a_get.equals(uri)){//拉取内容
                    return new Response(Response.Status.OK, "text/html", str);
                }else if(Conf.uri_push_w2a.equals(uri)){//推送内容
                    try {
                        if(parameters!=null && parameters.size()>0){
                            String content = parameters.get(Conf.key_push_text);
                            SetClipboardThread thread = new SetClipboardThread(content);
                            thread.start();
                        }
                        return new Response(Response.Status.OK, "text/html", "ok! 已推送至手机剪切板");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(Conf.uri_up_file_name.equals(uri)){//创建文件夹
                    if(parameters!=null && parameters.size()>0){
                        String path = parameters.get(Conf.key_path);
                        String fileName = parameters.get(Conf.key_fileName);
                        boolean b = FileUtils.createDir(path,fileName);
                        String str_ok = "{\n" +"\"flag\":\"1\",\n" +"\"msg\":\"ok! 已成功创建\"\n" +"}";
                        String str_error = "{\n" +"\"flag\":\"0\",\n" +"\"msg\":\"创建目录出错!可能由于您没有权限在此目录创建文件夹\"\n" +"}";
                        if(b){
                            return new Response(Response.Status.OK, "application/json", str_ok);
                        }else {
                            return new Response(Response.Status.OK, "application/json", str_error);
                        }
                    }
                } else if(Conf.uri_del_dir.equals(uri)){
                    if(parameters!=null && parameters.size()>0){
                        String path = parameters.get(Conf.key_path);
                        boolean b = FileUtils.delDir(path);
                        if(b){
                            return new Response(Response.Status.OK, "text/html", "删除成功!");
                        }else {
                            return new Response(Response.Status.OK, "text/html", "删除失败!\n可能由于您没有权限在此目录删除文件夹");
                        }
                    }
                } else if(Conf.uri_get_project_catch_dir.equals(uri)){//获取项目的储存卡缓存路径
                    String path = FileUtils.getAppStorageDirectory();
                    return new Response(Response.Status.OK, "text/html", path);
                }else if(Conf.uri_get_file_catch_dir.equals(uri)){
                    if(parameters!=null && parameters.size()>0){
                        String path = "";
                        String json="";
                        if(parameters.get(Conf.key_file_type).equals(Conf.flag_qq)){
                            path = FileUtils.getQQCarchDir();
                        }else {
                            path = FileUtils.getWXCarchDir();
                        }

                        if(path ==null){
                            json = "{\n" +"\"flag\":\"0\"\n" + "}";
                        }else {
                            json = "{\n" +
                                    "\"flag\":\"1\",\n" +
                                    "\"path\":\""+path+"\"\n" +
                                    "}";
                        }

                        L.e(path);
                        return new Response(Response.Status.OK, "application/json", json);
                    }
                } else if(Conf.uri_phone_info.equals(uri)){
                    Map<String,String> map = new HashMap<>();
                    map.put("MODEL",Build.MODEL);
                    map.put("SDK",android.os.Build.VERSION.RELEASE);
                    String json = gson.toJson(map);
                    return new Response(Response.Status.OK, "application/json", json);

                }else if (Conf.uri_down_load.equals(uri)){
                    try {
                        if(parameters!=null && parameters.size()>0){
                            String path = parameters.get(Conf.key_download_file_path);
                            FileInputStream fileInputStream = new FileInputStream(new File(path));
                            return new Response(Response.Status.OK, MIME_DEFAULT_BINARY,fileInputStream);
                        }else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    FileItemInfoBean fileItemInfoBean = new FileItemInfoBean();
                    if(FileUtils.isDir(uri)){
                        List<FileItemInfo> allFileOnPath = FileUtils.getAllFileOnPath(uri);
                        if(allFileOnPath!=null && allFileOnPath.size()>0){
                            Collections.sort(allFileOnPath);
                        }
                        fileItemInfoBean.setIsDir(1);
                        fileItemInfoBean.setList(allFileOnPath);
                    }else{
                        fileItemInfoBean.setIsDir(0);
                    }
                    String allItem = gson.toJson(fileItemInfoBean);
                    return new Response(Response.Status.OK, "application/json", allItem);

                }
            }
        }
//        return new NanoHTTPD.Response("hello,world!");
        return new Response("");
    }
    static  String str;

    class GetClipboardThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            str =  ClipboardUtils.getInstance(MyApplication.getAppContext()).getClipboardContent();
            Looper.loop();
        }
    }


    class SetClipboardThread extends Thread{
        private String content ;
        public SetClipboardThread(String content){
            this.content=content;
        }
        @Override
        public void run() {
            Looper.prepare();
            ClipboardUtils.getInstance(MyApplication.getAppContext()).setClipboardContent(content);
            Looper.loop();
        }
    }
}