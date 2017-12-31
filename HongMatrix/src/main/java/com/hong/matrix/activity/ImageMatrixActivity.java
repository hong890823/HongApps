package com.hong.matrix.activity;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.hong.matrix.view.ImageMatrixView;
import com.hong.matrix.R;

public class ImageMatrixActivity extends Activity {
    private EditText[] edits = new EditText[9];
    private float[] matrixs = new float[9];

    private ImageMatrixView matrixView;
    private GridLayout matrixLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        matrixView = (ImageMatrixView)findViewById(R.id.image_view);
        matrixLayout = (GridLayout)findViewById(R.id.matrix_layout);
        matrixLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = matrixLayout.getWidth();
                int height = matrixLayout.getHeight();

                for(int i=0;i<9;i++){
                    EditText edit = new EditText(ImageMatrixActivity.this);
                    edit.setWidth(width/3);
                    edit.setHeight(height/3);
                    edit.setGravity(Gravity.CENTER);
                    edits[i] = edit;
                    matrixLayout.addView(edits[i]);
                }
                initMatrix();
            }
        });
    }

    private void initMatrix(){
        for(int i=0;i<edits.length;i++){
            if(i%4==0){
                edits[i].setText(String.valueOf(1));
            }else{
                edits[i].setText(String.valueOf(0));
            }
        }
    }

    private Matrix getMatrix(){
        for(int i=0;i<edits.length;i++){
            matrixs[i] = Float.valueOf(edits[i].getText().toString());
        }
        Matrix matrix = new Matrix();
        matrix.setValues(matrixs);
        return matrix;
    }

    public void changeMatrix(View view){
        matrixView.setMatrix(getMatrix());
        matrixView.invalidate();
    }

    public void resetMatrix(View view){
        initMatrix();
        matrixView.setMatrix(getMatrix());
        matrixView.invalidate();
    }

}
