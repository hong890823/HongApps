package com.hong.hongtreeview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hong.hongtreeview.R;
import com.hong.hongtreeview.util.Node;
import com.hong.hongtreeview.util.TreeAdapter;

import java.util.List;

/**
 * Created by Hong on 2016/7/21.
 */
public class SimpleTreeAdapter extends TreeAdapter{

    public <T> SimpleTreeAdapter(Context context, ListView listView, List<T> datas, int defaultLevel) throws IllegalAccessException {
        super(context, listView, datas, defaultLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_tree_node,null);
            holder.icon = (ImageView) view.findViewById(R.id.item_icon);
            holder.label = (TextView) view.findViewById(R.id.item_label);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.icon.setImageResource(node.getIcon());
        holder.label.setText(node.getName());
        return view;
    }

    class ViewHolder{
        ImageView icon;
        TextView label;
    }

}
