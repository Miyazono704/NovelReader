package com.example.NovelReader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static Bitmap getImage(String url) throws Exception {
        Bitmap bitmap=null;
        //1、创建URL对象
        URL a=new URL(url);
        //2、打开连接，获取HttpURLConnection
        HttpURLConnection connection= (HttpURLConnection) a.openConnection();
        //3、请求成功，获取输入流
     //   System.out.println("响应码"+connection.getResponseCode());
        Log.d("net",connection.getResponseMessage()+"");
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
            InputStream in=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(in);
            return bitmap;
        }
        return null;
       // InputStream in=connection.getInputStream();

    }

}
