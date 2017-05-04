/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myfinal.cxy.wirelesstransmission.utils;


import android.os.Environment;

import com.myfinal.cxy.wirelesstransmission.MyApplication;
import com.myfinal.cxy.wirelesstransmission.bean.FileItemInfo;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class FileUtils {


    public static String getAppUploadFile(){
        return FileUtils.getAppStorageDirectory()+File.separator+"app-debug.apk";
    }

    public static String getAppStorageDirectory(){
        String path = Environment.getExternalStorageDirectory().getPath();
        if(path==null) return "";
        File file = new File(path + File.separator + "WirelessTransmission");
        if(!file.exists()){
            boolean mkdirs = file.mkdirs();
            if(!mkdirs)return "";

        }
        return file.getAbsolutePath() ;
    }

    public static List<FileItemInfo> getAllFileOnPath(String path){
        File file = new File(path);
        if(!file.exists()||!file.isDirectory()){
            return null ;
        }
        File[] files = file.listFiles();
        if(files==null)return null;
        List<FileItemInfo> list = new ArrayList<>();
        FileItemInfo itemInfo ;
        for(File f :files){
            itemInfo =new FileItemInfo();
            itemInfo.setName(f.getName());
            itemInfo.setAbsPath(f.getAbsolutePath());
            itemInfo.setFileType(analyzeFile(f));
            list.add(itemInfo);
            itemInfo = null ;
        }
        return list ;
    }

    private static int analyzeFile(File f) {
        if(f.isDirectory()){
            return FileItemInfo.Type.directory;
        }else if(f.getName().indexOf(".")==-1){
            return FileItemInfo.Type.unknow;
        }else {
            String substring = f.getName().substring(f.getName().lastIndexOf(".") + 1);
            if(substring!=null && !substring.equals("")){
                L.e(substring+"\t"+substring.toUpperCase(Locale.ENGLISH));
                Integer integer = MIMEUtils.get(substring.toUpperCase(Locale.ENGLISH));
                return integer.intValue();
            }else{
                return FileItemInfo.Type.unknow;
            }
        }
    }

    /**
     * 读取Assets文件
     *
     * @param fileName
     * @return
     */
    public static byte[] readAssets(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            return null;
        }
        byte[] buffer = null;
        try {
            InputStream fin = MyApplication.getAppContext().getAssets().open(fileName);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return buffer;
        }
    }


    public static boolean createDir(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if(!file.exists()){
            return file.mkdir();
        }
        return false;
    }

    public static boolean delDir(String path) {
        File file = new File(path);
        return deleteDir(file);
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if(children!=null){
                //递归删除目录中的子目录下
                for (int i=0; i<children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static String getQQCarchDir() {
        String path = Environment.getExternalStorageDirectory().getPath();
        if(path==null) return null;

        File file = new File(path + File.separator + "tencent" + File.separator + "QQfile_recv");
        if(file.isDirectory()){
            if(file.exists()){
                return file.getAbsolutePath();
            }
        }

        return null;
    }

    public static String getWXCarchDir() {
        String path = Environment.getExternalStorageDirectory().getPath();
        if(path==null) return null;
        File file = new File(path + File.separator + "tencent" + File.separator + "MicroMsg"+ File.separator + "Download");
        if(file.isDirectory()){
            if(file.exists()){
                return file.getAbsolutePath();
            }
        }

        return null;
    }


    public static boolean isDir(String uri) {
        File file = new File(uri);
        if(file.isDirectory()){
            return true ;
        }else {
            return false;
        }

    }

    public static File createOutFile(String fileName) {
        String path =getAppStorageDirectory();
        if(path==null) return null;
        return new File(path+File.separator+fileName);
    }

    public static boolean delFile(String path) {
        if(path==null ||path.equals(""))return true ;
        File file= new File(path);
        if(file.isFile()&& file.exists()){
                return file.delete();
        }
        return false;
    }
}