package com.example.NovelReader.wode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.NovelReader.HttpUtils;
import com.example.NovelReader.denglu.MainActivity;
import com.example.NovelReader.MyBRReceiver;
import com.example.NovelReader.R;
import com.example.NovelReader.Tips;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment4 extends Fragment {

    // 用户名
    @BindView(R.id.myUserNickName)
    TextView nickname;
    // 账号
    @BindView(R.id.myUserName)
    TextView username;
    @BindView(R.id.myUserHead)
    CircleImageView myhead;

    @BindView(R.id.myOrder_imgV)
    ImageView myOrder_imgV;
    @BindView(R.id.igmtv)
    ImageView igmtv;
    @BindView(R.id.SetGreen)
    TextView setgreen;
    @BindView(R.id.bg)
    ScrollView bg;
    @BindView(R.id.z)
    TextView z;
    @BindView(R.id.myGeneralText)
    TextView setting;
    @BindView(R.id.myModifyView)
    LinearLayout modifyView;

    private MyBRReceiver myReceiver;
    Handler handler=new Handler(){
        //处理消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bitmap bitmap= (Bitmap) msg.obj; //向下转型，父类-->子类
            myhead.setImageBitmap(bitmap);
        }
    };

    public MyFragment4() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me,container,false);

        ButterKnife.bind(this, view);
        myReceiver = new MyBRReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getContext().registerReceiver(myReceiver,intentFilter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    // 获取当前用户名

    @SuppressLint("SetTextI18n")
    private  void initView() {
        //getArguments()为空,想办法解决了
        String name = getActivity().getIntent().getStringExtra("name");
        //activiy1--->activity2--->fragment 我们有时用fragment时使用xml来直接绑定fragment，那么在activity执行onCreat方法的时候fragment就已经被实例化，即使在下面执行代码，已经被绑定的fragment也无法拿到bundle；利用Activity直接获得bundle
        nickname.setText("用户名: "+name);
        username.setText("账号ID: "+name);
    }
    //退出
    @OnClick(R.id.logoutBtn)
    void clickLogout() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
    //商城
    @OnClick(R.id.myOrder_imgV)
    void click1(){
        ConnectivityManager connectivityManager = (ConnectivityManager)  getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            Uri uri = Uri.parse("https://www.taobao.com/");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
        else
        {
            Tips.show("网络未连接！");
        }
    }
    //代付款
    @OnClick(R.id.igmtv)
    void click2(){
        ConnectivityManager connectivityManager = (ConnectivityManager)  getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
        Uri uri = Uri.parse("https://main.m.taobao.com/cart/index.html?cartFrom=taobao_client&spm=a215s.7406091.toolbar.i1");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
        }
        else
        {
            Tips.show("网络未连接！");
        }
    }
    //网络设置
    @OnClick(R.id.myGeneralText)
    void click3(){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }
    //更换头像
    @OnClick(R.id.z)
    void click4(){
        String url="https://img.52z.com/upload/news/image/20180313/20180313084417_73944.jpg";
        startImageThread(url);
        Tips.show("头像更换成功！");
    }
    //护眼模式
    @OnClick(R.id.SetGreen)
    void click5(){
        bg.setBackground(this.getResources().getDrawable(R.color.bg_green));
        Tips.show("护眼模式，启动！");
    }
    //修改地址
    @OnClick(R.id.myModifyText)
    void clickShowModify() {
        if (modifyView.getVisibility() == View.GONE) {
            modifyView.setVisibility(View.VISIBLE);//VISIBLE--->可见。
        } else {
            modifyView.setVisibility(View.GONE);//GONE---->不可见，但这个View在ViewGroupt中不保留位置，重新layout,那后面的view就会取代他的位置。
        }
    }
    //销毁广播
    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(myReceiver);
    }
    public void startImageThread(String url){
        //创建子线程
        Thread thread = new Thread(){

            //线程的主体
            @Override
            public void run() {
                try {
                    Bitmap bitmap= HttpUtils.getImage(url);
                    //获取消息对象
                    Message msg=handler.obtainMessage();
                    msg.obj=bitmap; //向上转型，子类--》父类
                    //发送消息
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //开启线程
        thread.start();
    }
}
