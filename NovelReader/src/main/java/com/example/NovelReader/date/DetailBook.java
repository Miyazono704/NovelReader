package com.example.NovelReader.date;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.R;
import com.example.NovelReader.Tips;
import com.example.NovelReader.fileServe;
import com.example.NovelReader.denglu.good;

public class DetailBook extends AppCompatActivity {
    RadioButton Shang,Menu,Next;
    private TextView Book;
    fileServe fileService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);
        Shang=findViewById(R.id.shang);
        Menu=findViewById(R.id.menu);
        Next=findViewById(R.id.next);
        Book=findViewById(R.id.bookview);
        Book.setMovementMethod(new ScrollingMovementMethod());
        fileService=new fileServe(this);

        String content=null;
        try {
            content=fileService.read("凡骨.text");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Book.setText(content);
        Shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tips.show("已是第一章！");
            }
        });
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailBook.this, good.class);
                startActivity(intent);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tips.show("没有更多了！");
            }
        });
    }
}
