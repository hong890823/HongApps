package com.hong.hongtreeview.bean;

import com.hong.hongtreeview.annotation.TreeNodeId;
import com.hong.hongtreeview.annotation.TreeNodeLabel;
import com.hong.hongtreeview.annotation.TreeNodePid;

/**
 * Created by Hong on 2016/7/19.
 * 组织结构实体
 */
public class OrgBean {
    @TreeNodeId(type=Integer.class)
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;

}
