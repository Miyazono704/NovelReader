package com.example.NovelReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.NovelReader.javabean.User;

public class MYsqliteopenhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="MYsqlite.db";


    private static final String create_users="create table users(name varchar(32),password varchar(32))";


    public MYsqliteopenhelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }
    //数据库第一次创建时被调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_users);
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean exist(String name){
        SQLiteDatabase db1 = getWritableDatabase();
        //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
        Cursor users = db1.query("users", null, "name = ?", new String[]{ name }, null, null, null);
        if (users.moveToFirst()){//指针移动到第一行,成功返回true,也说明有数据
              return true;
            }

        return false;

    }

    public long register(User u){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",u.getName());
        cv.put("password",u.getPassword());
        //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
        long users = db.insert("users", null, cv);
        return users;

    }



    public boolean login(String name,String password){
        SQLiteDatabase db1 = getWritableDatabase();
        boolean result =false;
        //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
        Cursor users = db1.query("users", null, "name like ?", new String[]{name}, null, null, null);
        if (users !=null){
            while (users.moveToNext()){
                String password1 = users.getString(1);//光标从第一列开始读
                result=password1.equals(password);
                return result;

            }

        }
return false;

    }
    public int update(String name,String NEWpassword,String password){
        SQLiteDatabase db2 = getWritableDatabase();
        ContentValues cv1=new ContentValues();//和bundle类似
        boolean login = login(name, password);
        if (login){
            cv1.put("name",name);
            cv1.put("password",NEWpassword);
        }else {
            int o=-1;
            return o;
        }
        //参数依次是表名，修改后的值，where条件，以及约束，如果不指定三四两个参数，会更改所有行
        int i = db2.update("users", cv1, "name= ?", new String[]{name});
        return i;
    }

}
