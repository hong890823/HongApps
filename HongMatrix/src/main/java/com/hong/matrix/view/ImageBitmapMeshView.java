package com.hong.matrix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.hong.matrix.R;

/**
 * Created by Hong on 2016/5/26.
 */
public class ImageBitmapMeshView extends View{
    private static int WIDTH = 200;//图片横向分割单元格数
    private static int HEIGHT = 200;//图片纵向分割单元格数
    private static int COUNT = (WIDTH+1)*(HEIGHT+1);//图片分割之后的所有交叉点个数
    private float[] verts = new float[COUNT*2];//可操作的交叉点存储数组（第奇数个为坐标X的值，偶数为坐标Y的值）
    private float[] orgs = new float[COUNT*2];//原始的交叉点存储数组（第奇数个为坐标X的值，偶数为坐标Y的值）

    private Bitmap bitmap;
    public ImageBitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        int index = 0;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iverson);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        for(int i=0;i<HEIGHT+1;i++){
            float y = bitmapHeight*i/HEIGHT;
            for(int j=0;j<WIDTH+1;j++){
                float x = bitmapWidth*j/WIDTH;
                orgs[index*2] = verts[index*2] = x;
                orgs[index*2+1] = verts[index*2+1] = y;
                index+=1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<HEIGHT+1;i++){
            for(int j=0;j<WIDTH+1;j++){
                verts[(i*(WIDTH+1)+j)*2] +=0;
                float offsetY =(float)Math.sin((float)j/WIDTH*2*Math.PI);
                verts[(i*(WIDTH+1)+j)*2+1] += orgs[(i*(WIDTH+1)+j)*2+1]+offsetY*100;
            }
        }
        canvas.drawBitmapMesh(bitmap,WIDTH,HEIGHT,verts,0,null,0,null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }

}
