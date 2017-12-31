package com.hong.hongtreeview.bean;

import com.hong.hongtreeview.annotation.TreeNodeId;
import com.hong.hongtreeview.annotation.TreeNodeLabel;
import com.hong.hongtreeview.annotation.TreeNodePid;

/**
 * Created by Hong on 2016/7/19.
 * 文件结构实体
 */
public class FileBean {
    @TreeNodeId(type=Integer.class)
    private int id;
    @TreeNodePid
    private int pId;
    @TreeNodeLabel
    private String label;

    private String desc;

    public FileBean(int id,int pId,String label){
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
