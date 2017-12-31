package com.hong.hongconnectassignwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HongWifi";
    private static final String ASSIGN_WIFI_SSID = "zst-guest";
    private static final String ACTION_1 = WifiManager.WIFI_STATE_CHANGED_ACTION;
    private static final String ACTION_2 = WifiManager.NETWORK_STATE_CHANGED_ACTION;

    private WifiReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        registerWifiReceiver(wm);
        openWifi(wm);
    }

    /*
    * 把一个WifiReceiver对象注册成为Wifi改变的广播接受者
    * */
    private void registerWifiReceiver(WifiManager wm){
        wifiReceiver = new WifiReceiver(wm);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_1);
        filter.addAction(ACTION_2);
        registerReceiver(wifiReceiver, filter);
    }

    /*
    * 如果WiFi关闭，申请打开WiFi
    * */
    private void openWifi(WifiManager wm){
        if(!wm.isWifiEnabled()){
            wm.setWifiEnabled(true);
        }
    }

    /*
    * 如果WiFi开启，则获取可用WiFi列表
    * */
    private void getAvailableWifiList(WifiManager wm){
        if(wm.isWifiEnabled()){
            if(!ASSIGN_WIFI_SSID.equals(wm.getConnectionInfo().getBSSID())){//连接的Wifi不是指定Wifi
                List<ScanResult> wifiList = wm.getScanResults();
                while(wifiList.size()==0){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    wifiList = wm.getScanResults();
                }
                for(ScanResult result:wifiList){
                    Log.e(TAG, "getAvailableWifiList: "+result.toString());
                    if(ASSIGN_WIFI_SSID.equals(result.SSID)){
                        connectAssignWifi(wm);
                        break;
                    }
                }
            }
        }else{
            Toast.makeText(MainActivity.this, "请允许开启Wifi", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    * 连接指定Wifi
    * */
    private void connectAssignWifi(WifiManager wm){
        boolean isConfig = true;
        List<WifiConfiguration> configs = wm.getConfiguredNetworks();
        for(WifiConfiguration config:configs){
            Log.e(TAG, "connectAssignWifi: "+config.SSID);
            if(("\""+ASSIGN_WIFI_SSID+"\"").equals(config.SSID)){
                if(wm.enableNetwork(config.networkId,true)){
                    isConfig=false;
                    Toast.makeText(MainActivity.this, "已将网络切换至"+ASSIGN_WIFI_SSID, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

        if(isConfig){//扫描的到该网络，但是获取不到该网络的配置信息
            WifiConfiguration info = CreateWifiInfo(wm, "zst-guest", "zstong2012", 3);
            wm.addNetwork(info);
            connectAssignWifi(wm);//递归调用可以，直接连接的话木有好使
            Log.e(TAG, "connectAssignWifi: "+"加入"+ASSIGN_WIFI_SSID+"网络连接");
        }
    }

    public WifiConfiguration CreateWifiInfo(WifiManager wm,String SSID, String Password, int Type){
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

//        WifiConfiguration tempConfig = this.IsExsits(wm,SSID);
//        if(tempConfig != null) {
//            wm.removeNetwork(tempConfig.networkId);
//        }
        if(Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if(Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0]= "\""+Password+"\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if(Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\""+Password+"\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(WifiManager wm,String SSID){
        List<WifiConfiguration> existingConfigs = wm.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs)
        {
            if (existingConfig.SSID.equals("\""+SSID+"\""))
            {
                return existingConfig;
            }
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
    }


    /*
     *接收系统Wifi改变的广播接收类*
     * */

    class WifiReceiver extends BroadcastReceiver{

        WifiManager wm;

        public WifiReceiver(WifiManager wm){
            this.wm = wm;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(ACTION_1.equals(action)){
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.e(TAG, "onReceive: "+"WIFI_STATE_ENABLED");
                        getAvailableWifiList(wm);
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        break;
                }

            }

            if(ACTION_2.equals(action)){
                NetworkInfo info=intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                Log.e(TAG, "当前连接状态是: "+ info.getState());
            }

        }

    }


}
