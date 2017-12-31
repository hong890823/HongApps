package com.hong.hongtreeview.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hong on 2016/7/19.
 */
public class Node {
    private int id;

    //根节点pI是0
    private int pId;

    private String name;

    //Node层级
    private int level;

    //Node图标
    private int icon;

    //是否展开
    private boolean isExpand;

    //Node的父Node
    private Node parent;

    //Node的子Node集合
    private List<Node> childrenList ;

    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.childrenList = new ArrayList<>();
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

    /**
     * 重写返回层级的方法（根据父节点来判断层级,根节点为0）
     * */
    public int getLevel() {
        return parent==null?0:parent.getLevel()+1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 重写设置收缩展开的方法，当收缩的时候循环递归关闭所有子节点
     * */
    public void setExpand(boolean expand) {
        isExpand = expand;
        if(!expand){
            for(Node node:childrenList){
                node.setExpand(false);
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Node> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * 该节点是否是根节点
     * */
    public boolean isRoot(){
        return parent==null;
    }

    /**
     * 父节点是否是展开的
     * */
    public boolean isParentExpand(){
        if(parent==null)return false;
        return parent.isExpand;
    }

    /**
     * 是否是叶节点
     * */
    public boolean isLeaf(){
        return childrenList.size()==0;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
