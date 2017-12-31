package com.hong.hongbarrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BarrageView barrageView;
    private Button switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barrageView = (BarrageView)findViewById(R.id.barrage_view);
        switchView = (Button) findViewById(R.id.switch_view);
    }

    public void swtichBarrage(View view){
        if(barrageView.isBarrage()){
            barrageView.stopBarrage();
            switchView.setText("开始弹幕");
        }else{
            barrageView.startBarrage();
            switchView.setText("停止弹幕");
        }

    }

}
