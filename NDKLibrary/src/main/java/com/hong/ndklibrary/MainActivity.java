package com.hong.ndklibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NDKString ndkString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ndkString = new NDKString();
    }

    public void getFromC(View view){
        String content = NDKString.getFromC();
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    public void uploadFile(View view){
        ndkString.uploadFile("/mnt/sdcard/Hong.txt");
    }

    public void updateIntArray(View view){
        int[] array = new int[]{0,1,2,3,4,5,6,7,8,9};
        int[] newArray = ndkString.updateIntArray(array);
        Toast.makeText(MainActivity.this, newArray.toString(), Toast.LENGTH_SHORT).show();
    }
}
