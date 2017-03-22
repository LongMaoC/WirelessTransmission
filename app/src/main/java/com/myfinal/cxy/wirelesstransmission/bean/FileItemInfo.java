package com.myfinal.cxy.wirelesstransmission.bean;

/**
 * Created by cxy on 17-3-15.
 */
public class FileItemInfo implements Comparable<FileItemInfo>{


    @Override
    public int compareTo(FileItemInfo o2) {
        if(getFileType() == FileItemInfo.Type.directory && o2.getFileType() != FileItemInfo.Type.directory){
            return -1 ;
        }else if(getFileType() != FileItemInfo.Type.directory && o2.getFileType() == FileItemInfo.Type.directory){
            return 1 ;
        }
        return -o2.getName().compareTo(getName()) ;
    }

    public interface Type {
        int unknow = 0;
        int music = 1;
        int movie = 2;
        int image = 3;
        int txt = 4;
        int directory = 5;
        int xml = 6;
        int rar = 7;
        int apk = 8;
        int office = 9;
        int jar = 10;
        int html= 11;
        int log= 12;
        int sys_file =13 ;
        int sys_dir =14 ;
        int pdf =15 ;

    }

    private int fileType = 0;
    private String absPath;
    private String name;

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileItemInfo{" +
                "fileType=" + fileType +
                ", absPath='" + absPath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
