package com.hong.hongtreeview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hong.hongtreeview.adapter.SimpleTreeAdapter;
import com.hong.hongtreeview.bean.FileBean;
import com.hong.hongtreeview.util.Node;
import com.hong.hongtreeview.util.TreeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView treeView;
    private SimpleTreeAdapter treeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        treeView = (ListView)findViewById(R.id.tree_list_view);
        try {
            treeAdapter = new SimpleTreeAdapter(this,treeView,initDatas(),1);
            treeAdapter.setConvertViewPadding(30,5,5,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        treeView.setAdapter(treeAdapter);
        initEvent();
    }

    public  List<FileBean>  initDatas(){
        List<FileBean> beans = new ArrayList<>();
        beans.add(new FileBean(1,0,"测试0-1"));
        beans.add(new FileBean(2,0,"测试0-2"));
        beans.add(new FileBean(3,0,"测试0-3"));
        beans.add(new FileBean(4,0,"测试0-4"));
        beans.add(new FileBean(5,0,"测试0-5"));
        beans.add(new FileBean(6,0,"测试0-6"));
        beans.add(new FileBean(7,0,"测试0-7"));

        beans.add(new FileBean(8,3,"测试0-3-1"));
        beans.add(new FileBean(9,3,"测试0-3-2"));
        beans.add(new FileBean(10,3,"测试0-3-3"));
        beans.add(new FileBean(11,3,"测试0-3-4"));

        beans.add(new FileBean(8,11,"测试0-3-4-1"));
        beans.add(new FileBean(9,11,"测试0-3-4-2"));
        beans.add(new FileBean(10,11,"测试0-3-4-3"));
        return beans;
    }

    public void initEvent(){
        if (treeAdapter != null) {
            treeAdapter.setOnTreeNodeClickListener(new TreeAdapter.OnTreeNodeClickListener() {
                @Override
                public void onTreeNodeClick(Node node, int position) {
                    if(node.isLeaf())
                        Toast.makeText(MainActivity.this, "单击了"+node.getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        treeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("Add Node").setView(editText).setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        treeAdapter.addNode(position,editText.getText().toString());
                }
                }).setNegativeButton("Cancel",null).create().show();
                return false;
            }
        });
    }


}
