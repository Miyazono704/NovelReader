package com.example.NovelReader.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.NovelReader.MyFragmentPagerAdapter;
import com.example.NovelReader.R;

public class good extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    //UI Objects
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private RadioButton rb_setting;
    private ViewPager2 vpager;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        //获取所在fragment 的父容器的管理器
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        bindViews();
        //默认首页被点击
        rb_channel.setChecked(true);
        Intent intent=getIntent();
        String name = intent.getStringExtra("name");
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
    }

    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (ViewPager2) findViewById(R.id.vpager);
        vpager.setUserInputEnabled(false);//禁用滑动
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        // 这个方法可以可以监听到 ViewPager 2 的界面变化，进而去操作其他的控件。
        vpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageScrollStateChanged(int state) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
                if (state == 2) {
                    switch (vpager.getCurrentItem()) {
                        case PAGE_ONE:
                            rb_channel.setChecked(true);
                            break;
                        case PAGE_TWO:
                            rb_message.setChecked(true);
                            break;
                        case PAGE_THREE:
                            rb_better.setChecked(true);
                            break;
                        case PAGE_FOUR:
                            rb_setting.setChecked(true);
                            break;
                    }
                }
            }
        });
    }
    //重写RadioGroup的onCheckedChange，判断checkid即可知道点击的是哪个RadioButton！
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_channel:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_better:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rb_setting:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }



}