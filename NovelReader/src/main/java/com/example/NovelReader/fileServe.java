package com.example.NovelReader;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class fileServe {
    Context context;
    public fileServe(Context context){
        this.context=context;

    }
    public void write(String fileName,String content) throws Exception {
        //这里我们使用私有模式,创建出来的文件只能被本应用访问,还会覆盖原文件哦
        FileOutputStream out=context.openFileOutput(fileName,Context.MODE_PRIVATE);
        out.write(content.getBytes());//将String字符串以字节流的形式写入到输出流中
        out.close(); //关闭输出流
    }
    public String read(String fileName) throws Exception {
        //打开文件输入流
        FileInputStream in=context.openFileInput(fileName);
        byte buffer[]=new byte[1024];
        StringBuffer sb=new StringBuffer();
        int len;
        //读取文件内容:
        while((len=in.read(buffer))!=-1){
           sb.append (new String(buffer,0,len));
        }
        //关闭输入流
        in.close();
        return sb.toString();
    }
}
