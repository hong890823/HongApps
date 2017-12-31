package com.hong.hongtreeview.util;

import com.hong.hongtreeview.R;
import com.hong.hongtreeview.annotation.TreeNodeId;
import com.hong.hongtreeview.annotation.TreeNodeLabel;
import com.hong.hongtreeview.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hong on 2016/7/19.
 */
public class TreeHelper {

    /**
     * 把实体类型转成Node节点类型(用户数据转成树形数据)
     * */
    private static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException{
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        for(T t:datas){
            int nodeId = -1;
            int nodePid = -1;
            String nodeName = null;

            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();//获取该类的所有属性
            for(Field field:fields){
                field.setAccessible(true);//将原本private的属性强制设为可操作
                //如果某个属性的TreeNodeId注解不为空，则获取该属性的值
                if(field.getAnnotation(TreeNodeId.class)!=null)nodeId = field.getInt(t);
                if(field.getAnnotation(TreeNodePid.class)!=null)nodePid = field.getInt(t);
                if(field.getAnnotation(TreeNodeLabel.class)!=null)nodeName = (String)field.get(t);
            }

            node = new Node(nodeId,nodePid,nodeName);
            nodes.add(node);
        }

        setNodeRelation(nodes);

        for(Node n:nodes){
            setNodeIcon(n);
        }

        return nodes;
    }

    /**
     * 设置节点的父子关系
     * */
    private static void setNodeRelation(List<Node> nodes){
        for(int i=0;i<nodes.size();i++){
            Node n = nodes.get(i);
            for(int j=i+1;j<nodes.size();j++){
                Node m = nodes.get(j);
                if(n.getId()==m.getpId()){
                    n.getChildrenList().add(m);
                    m.setParent(n);
                }else if(n.getpId()==m.getId()){
                    m.getChildrenList().add(n);
                    n.setParent(m);
                }
            }
        }
    }


    /**
     * 为节点设置相应的图标
     * */
    private static void setNodeIcon(Node node){
        if(node.getChildrenList().size()>0){
            if(node.isExpand()){
                node.setIcon(R.mipmap.tree_ex);
            }else{
                node.setIcon(R.mipmap.tree_ec);
            }
        }
    }

    /**
     * 获取根节点集合
     * */
    private static List<Node> getRootNodes(List<Node> nodes){
        List<Node> roots = new ArrayList<>();
        for(Node node:nodes){
            if(node.isRoot())roots.add(node);
        }
        return roots;
    }

    /**
     *递归的将根节点中的所有孩子都添加进去
     * @param defaultLevel 是一个自定义的默认可以展开的最大级数（当然level级数是从根开始越来越大的）
     * */
    private static void addChildrenNodes(List<Node> result,Node node,int defaultLevel,int currentLevel){
        result.add(node);
        if(defaultLevel>=currentLevel)node.setExpand(true);//小于默认级数的都可以展开
        if(node.isLeaf())return;
        for(int i=0;i<node.getChildrenList().size();i++){
            addChildrenNodes(result,node.getChildrenList().get(i),defaultLevel,currentLevel+1);
        }

    }

    /**
     *过滤出可以展示出来的Node节点
     * */
    public static List<Node> filterVisiableNode(List<Node> nodes){
        List<Node> result = new ArrayList<>();
        for(Node node:nodes){
            if(node.isRoot() || node.isParentExpand()){
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 获取具有父子关系并且已经排序好的节点(对外公布的方法)
     * */
    public static <T> List<Node> getSortedNodes(List<T> datas,int defaultLevel) throws IllegalAccessException{
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertDatas2Nodes(datas);

        List<Node> rootNodes = getRootNodes(nodes);
        for(Node rootNode:rootNodes){
            addChildrenNodes(result,rootNode,defaultLevel,1);
        }
        return result;
    }


}
