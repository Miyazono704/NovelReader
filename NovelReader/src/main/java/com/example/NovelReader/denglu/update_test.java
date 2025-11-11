package com.example.NovelReader.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.MYsqliteopenhelper;
import com.example.NovelReader.R;

public class update_test extends AppCompatActivity {
private EditText name,password,repassword;
private Button update;
    private MYsqliteopenhelper mYsqliteopenhelper2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_test);
        mYsqliteopenhelper2 =new MYsqliteopenhelper(this);
        find();

    }

    private void find() {
        name =findViewById(R.id.etname2);
        password =findViewById(R.id.edpassword2);
        repassword =findViewById(R.id.repassword1);
    }

    public void xiugai(View view) {
        String s = name.getText().toString();
        String s1 = password.getText().toString();
        String s2 = repassword.getText().toString();
        int update = mYsqliteopenhelper2.update(s, s2, s1);
        if (update !=-1){
            Toast.makeText(this, "密码修改成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "密码修改失败！", Toast.LENGTH_SHORT).show();
        }
    }
}