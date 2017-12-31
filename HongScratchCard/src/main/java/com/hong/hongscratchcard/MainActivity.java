package com.hong.hongscratchcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.hong.hongscratchcard.view.ScratchCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.view_group);

        DisplayMetrics outMetrics = new DisplayMetrics();
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        viewGroup.addView(new ScratchCardView(this));
    }


}
