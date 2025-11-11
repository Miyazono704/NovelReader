package com.example.NovelReader.denglu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.MYsqliteopenhelper;
import com.example.NovelReader.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, register;
    private EditText name, password;
    private MYsqliteopenhelper mYsqliteopenhelper;
    private StringBuilder sb;
    private TextView query, update,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mYsqliteopenhelper = new MYsqliteopenhelper(this);
        find();

    }

    private void find() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        name = findViewById(R.id.edname);
        password = findViewById(R.id.edpassword);
        update = findViewById(R.id.update2);
        delete=findViewById(R.id.btn_delete);
        query=findViewById(R.id.btn_query);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase db = mYsqliteopenhelper.getWritableDatabase();
        int id = view.getId();
        switch (id) {
            case R.id.login:
                String s = name.getText().toString();
                String s1 = password.getText().toString();
                if(s.equals("")){
                    Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
                }else if(s1.equals("")){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else {
                    boolean login = mYsqliteopenhelper.login(s, s1);
                    if (login) {
                        Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, good.class);
                        i.putExtra("name",name.getText().toString());//读取用户名
                        startActivity(i);
                        name.setText("");
                        password.setText("");
                    } else {
                        Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

                //注册

            case R.id.register:
                Intent i1 = new Intent(this, com.example.NovelReader.denglu.register.class);
                startActivity(i1);
                break;

                //修改密码

            case R.id.update2:
                Intent i2 =new Intent(this, update_test.class);
                startActivity(i2);
                break;

                //注销

            case R.id.btn_delete:

                //2.创建对话框的标题、图标、显示文字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(null);
                builder.setTitle("注意：");
                builder.setMessage("此操作将会删除所有账号,确定要继续吗？");
                //3.创建对话框中的“确定”及其单击事件
                builder.setPositiveButton("确定", (dialogInterface, i) -> {
                    //参数依次是表名，以及where条件与约束
                    db.delete("users", null, null);
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                });
                //4.创建对话框中的“取消”及其单击事件
                builder.setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(MainActivity.this, "已取消", Toast.LENGTH_SHORT).show()).show();
                break;

                //忘记密码

            case R.id.btn_query:
                final EditText edit = new EditText(this);
                //文本对话框
                AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
                //依次设置标题，图标
                editDialog.setTitle("请输入管理员密码");
                editDialog.setIcon(R.mipmap.ic_launcher_round);
                //设置dialog布局
                editDialog.setView(edit);
                //设置按钮
                editDialog.setPositiveButton("确定"  ,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str="123456";
                                String ps=edit.getText().toString();
                                if(str.equals(ps)){
                                    sb = new StringBuilder();
                                     int i=0;
                                    String query = "SELECT * from users";
                                    Cursor cursor = db.rawQuery(query, null);//使用带占位符的SQL查询操作
                                    if (cursor.getCount() == 0) {//获得总得数据条数
                                        Toast.makeText(MainActivity.this, "当前无任何账户！", Toast.LENGTH_SHORT).show();
                                    }else{
                                        final String[] string =new String[]{"","","","","","",""};//总共显示7个账户
                                        //再创建AlertDialog.Builder对象；
                                        AlertDialog.Builder listDialog = new AlertDialog.Builder(MainActivity.this);
                                        while (cursor.moveToNext()){
                                            @SuppressLint("Range") String pid = cursor.getString(cursor.getColumnIndex("name"));
                                            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                                            string[i]=("账户:" + pid + "  " +"密码:" + password );
                                            i++;
                                        }
                                        listDialog.setTitle("所有账户:");
                                        listDialog.setIcon(null);
                                        //设置item 不能用setMessage()用setItems items : listItems[] -> 列表项数组listener -> 回调接口
                                        listDialog.setItems(string, (dialog1, which1) -> Toast.makeText(MainActivity.this,string[which1],Toast.LENGTH_SHORT).show());
                                        listDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();//关闭对话框
                                                    }
                                                });
                                        //调用create()方法创建这个对象，再调用show()方法将列表对话框显示出来
                                        listDialog.create().show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "密码错误，查询失败!",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                //调用create()方法创建这个对象，再调用show()方法将对话框显示出来
                editDialog.create().show();
                break;
        }
    }
}