package com.example.NovelReader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyBRReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //网络管理器负责查询网络连接状态以及在连接状态有变化的时候发出通知
        ConnectivityManager connectivityManager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //返回NetworkInfo，该类用于描述网络的状态
        if(networkInfo != null && networkInfo.isConnected())
        {
            Tips.show("网络已连接！");
        }
        else
        {
            Tips.show("网络未连接！");
        }
    }
}
