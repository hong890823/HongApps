package com.hong.hongtreeview.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Hong on 2016/7/20.
 */
public abstract class TreeAdapter extends BaseAdapter{
    public interface OnTreeNodeClickListener{
        void onTreeNodeClick(Node node,int position);
    }

    protected int left,top,right,bottom;
    protected Context context;
    protected ListView listView;
    protected LayoutInflater inflater;
    protected List<Node> visiableNodes;
    protected List<Node> allNodes;
    protected OnTreeNodeClickListener onTreeNodeClickListener;

    public <T> TreeAdapter(Context context,ListView listView,List<T> datas,int defaultLevel) throws IllegalAccessException{
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(context);
        allNodes = TreeHelper.getSortedNodes(datas,defaultLevel);
        visiableNodes = TreeHelper.filterVisiableNode(allNodes);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expandOrShrink(i);
                if(onTreeNodeClickListener!=null)onTreeNodeClickListener.onTreeNodeClick(visiableNodes.get(i),i);
            }
        });
    }

    @Override
    public int getCount() {
        return visiableNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return visiableNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node = visiableNodes.get(i);
        view = getConvertView(node,i,view,viewGroup);
        view.setPadding(node.getLevel()*left,top,right,bottom);
        return view;
    }

    public void expandOrShrink(int position){
        Node node = visiableNodes.get(position);
        if(node.isLeaf())return;
        node.setExpand(!node.isExpand());
        visiableNodes = TreeHelper.filterVisiableNode(allNodes);
        notifyDataSetChanged();
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public void setConvertViewPadding(int left,int top,int right,int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void addNode(int position,String name){
        Node node = visiableNodes.get(position);
        int realPosition = allNodes.indexOf(node);
        List<Node> childrenNodes = node.getChildrenList();

        Node extraNode = new Node(-1,node.getId(),name);
        extraNode.setParent(node);
        childrenNodes.add(extraNode);
        allNodes.add(realPosition+1,extraNode);

        //以上的逻辑中，对于总数据的顺序和关系已经设计好了
        visiableNodes = TreeHelper.filterVisiableNode(allNodes);
        notifyDataSetChanged();
    }

    public abstract View getConvertView(Node node,int position,View view,ViewGroup viewGroup);



}
