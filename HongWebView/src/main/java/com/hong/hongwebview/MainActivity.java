package com.hong.hongwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WebView";
    private static final String testURL = "http://www.html5tricks.com/demo/html5-canvas-web-draw/index.html";
    private static final String testURL1 = "http://123.56.200.29:8888/reg.dll";
    private static final String localURL = "file:///android_asset/test.html";//本地文件一定要改成Unicode编码格式的，否则乱码

    private HongWebView webView;
    private TextView titleView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (HongWebView) findViewById(R.id.webView);
        titleView = (TextView) findViewById(R.id.titleView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);

        webView.loadUrl(testURL);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleView.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: "+newProgress);
                progressBar.setProgress(newProgress);
            }

        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }


        });

    }

    public void webViewRefresh(View view){
        webView.reload();
    }


}
