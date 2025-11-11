package com.example.NovelReader.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //获取顶级视图,它包含整个屏幕，包括标题栏
        View decorView = getWindow().getDecorView();
        // 隐藏状态栏,全屏显示
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //设置状态栏属性
        decorView.setSystemUiVisibility(uiOptions);
        //创建多线程消息的函数延迟intent跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                //Handler是在UI线程中创建的，所以自然地依附在主线程中了,
                finish();
            }
        }, 2000); //延迟2秒，再运行Runnable的run()
    }
}