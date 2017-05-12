package com.easy.view.image;

/**
 * 对应文件夹
 * Created by Administrator on 2017/5/10.
 */

public class FolderBean {
    //当前文件夹路径
    private String dir;
    //第一张图片的路径
    private String firstImgPath;
    //当前文件夹名称
    private String name;
    //当前文件夹中图片数量
    private int count;

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getDir() {

        return dir;
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
