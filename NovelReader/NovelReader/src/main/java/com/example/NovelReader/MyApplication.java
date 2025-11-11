package com.example.NovelReader;

import android.app.Application;

import com.example.NovelReader.javabean.Book;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    /**
     * Application类上下文
     */
    private static MyApplication appContext;

    /**
     * 书架的图书从这里传到书架
     */
    private static List<Book> cartBooks;

    public static MyApplication getInstance() {
        return appContext;
    }

    public static List<Book> getCartBooks() {
        return cartBooks;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        // 初始化图书集合
        cartBooks = new ArrayList<>();
        // 初始化工具类
        Utils.init(this);
    }
}
