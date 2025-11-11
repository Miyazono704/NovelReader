package com.example.NovelReader.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.MYsqliteopenhelper;
import com.example.NovelReader.R;
import com.example.NovelReader.javabean.User;

public class register extends AppCompatActivity {
    private Button register1;
    private EditText name1,password1;
    private TextView tv;
    private MYsqliteopenhelper mYsqliteopenhelper1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mYsqliteopenhelper1 =new MYsqliteopenhelper(this);
        find();
        tv.setText(backtomain());
    }


    private void find() {
        tv=findViewById(R.id.tv);
        register1 = findViewById(R.id.register1);
        name1 = findViewById(R.id.etname);
        password1 = findViewById(R.id.edpassword1);
    }

    public void zhuche(View view) {
        String s = name1.getText().toString();
        String s1 = password1.getText().toString();
        if(s.equals("")){
            Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
        }else if(s1.equals("")){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else{
            User u = new User(s, s1);
            boolean exist= mYsqliteopenhelper1.exist(s);
        if (exist) {
            Toast.makeText(this, "账户已存在！", Toast.LENGTH_SHORT).show();
        }else {
            long l = mYsqliteopenhelper1.register(u);
            if (l != -1) {
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(this, MainActivity.class);
                startActivity(i3);
            } else {
                Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
            }
        }
        }
    }
//为了实现前面蓝色后面绿色我才使用文本超链接，不然可以直接使用onclick,用这个可以实现那种一大堆文字里面就服务协议点进去看的效果
    private SpannableString backtomain(){
        String str =  tv.getText().toString(); // 前包括后不包括
        //超链接的块对象
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(register.this, MainActivity.class);
                startActivity(i3);
            }

            //更新局部效果,取消下划线
            @Override
            public void updateDrawState(TextPaint ds) {

                ds.setUnderlineText(false);

            }
        };
        //创建Spannable对象
        SpannableString span = new SpannableString(str);
        //设置局部效果
        //(局部的效果对象，局部的起始位置，结束位置，包括方式) INCLUSIVE表示包裹， EXCLUSIVE不包括
        span.setSpan(clickableSpan,5,9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//此处为前包括后不包括
        span.setSpan(new ForegroundColorSpan(0xAB00FF00),5,9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//此处为前包括后不包括
        //将文本特效设置到文本框中
        tv.setText(span,TextView.BufferType.SPANNABLE);
        //设置触摸监听的解析对象,就是超链接可以点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return span;
    }
}