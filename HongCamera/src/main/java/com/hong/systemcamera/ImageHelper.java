package com.hong.systemcamera;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hong on 2016/5/23.
 */
public class ImageHelper {

    /*
    * 调节图像色调，饱和度，明度
    * */
    public static Bitmap handleImageEffect(Bitmap bm,float hum,float saturation,float lum){
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix humMatrix = new ColorMatrix();//调节色调
        humMatrix.setRotate(0,hum);
        humMatrix.setRotate(1,hum);
        humMatrix.setRotate(2,hum);

        ColorMatrix saturationMatrix = new ColorMatrix();//调节饱和度
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();//调节明度
        lumMatrix.setScale(lum,lum,lum,1);

        ColorMatrix imageMatrix = new ColorMatrix();//混合
        imageMatrix.postConcat(humMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));//混合后的ColorMatrix设置给画笔的颜色过滤器
        canvas.drawBitmap(bm,0,0,paint);//画图
        return bmp;
    }

    /*
    * 更改图像每个像素点的值从而达到特效效果
    * */
    public static Bitmap handleImageNative(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width*height];
        int[] newPx = new int[width*height];
        bmp.getPixels(oldPx,0,width,0,0,width,height);
        for(int i=0;i<width*height;i++){
            int r = Color.red(oldPx[i]);
            int g = Color.green(oldPx[i]);
            int b = Color.blue(oldPx[i]);
            int a = Color.alpha(oldPx[i]);

            r = 255-r;
            g = 255-g;
            b = 255-b;

            if(r>255){
                r=255;
            }else if(r<0){
                r=0;
            }
                if(g>255){
                g=255;
            }else if(g<0){
                g=0;
            }
            if(b>255){
                b=255;
            }else if(b<0){
                b=0;
            }

            newPx[i] = Color.argb(r,g,b,a);
        }

        bmp.setPixels(newPx,0,width,0,0,width,height);
        return bmp;
    }

    /**
     * 得到本地图片文件（该方法先是查询到所有图片的id和缩略图路径，然后再根据id查询原图的路径）
     * @param context
     * @return
     */
    public static ArrayList<HashMap<String,String>> getAllPictures(Context context) {
        ArrayList<HashMap<String,String>> picturemaps = new ArrayList<>();
        HashMap<String,String> picturemap;
        ContentResolver cr = context.getContentResolver();
        //先得到缩略图的URL和对应的图片id
        Cursor cursor = cr.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Thumbnails.IMAGE_ID,
                        MediaStore.Images.Thumbnails.DATA
                },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                picturemap = new HashMap<>();
                picturemap.put("image_id_path",cursor.getInt(0)+"");
                picturemap.put("thumbnail_path",cursor.getString(1));
                picturemaps.add(picturemap);
            } while (cursor.moveToNext());
            cursor.close();
        }
        //再得到正常图片的path
        for (int i = 0;i<picturemaps.size();i++) {
            picturemap = picturemaps.get(i);
            String media_id = picturemap.get("image_id_path");
            cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            MediaStore.Images.Media.DATA
                    },
                    MediaStore.Audio.Media._ID+"="+media_id,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                do {
                    picturemap.put("image_id",cursor.getString(0));
                    picturemaps.set(i,picturemap);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return picturemaps;
    }

}
