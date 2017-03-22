package com.myfinal.cxy.wirelesstransmission.bean;

import java.util.List;

/**
 * Created by cxy on 17-3-21.
 */

public class FileItemInfoBean {
    private List<FileItemInfo> list;
    private int isDir;//1是0否

    public List<FileItemInfo> getList() {
        return list;
    }

    public void setList(List<FileItemInfo> list) {
        this.list = list;
    }

    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }
}
