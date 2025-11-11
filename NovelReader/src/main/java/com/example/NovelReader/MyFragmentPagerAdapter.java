package com.example.NovelReader;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.NovelReader.bangdan.MyFragment2;
import com.example.NovelReader.denglu.good;
import com.example.NovelReader.shouye.MyFragment1;
import com.example.NovelReader.shujia.MyFragment3;
import com.example.NovelReader.wode.MyFragment4;

/**
碎片 ViewPager2只有一个适配器就是FragmentStateAdapter
 */
public class MyFragmentPagerAdapter extends FragmentStateAdapter {

    private final int PAGER_COUNT = 4;
    private MyFragment1 myFragment1 = null;
    private MyFragment2 myFragment2 = null;
    private MyFragment3 myFragment3 = null;
    private MyFragment4 myFragment4 = null;


    public MyFragmentPagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
        super(fm,lifecycle);
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();
        myFragment3 = new MyFragment3();
        myFragment4 = new MyFragment4();
    }
    //用来根据position创建fragment
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case good.PAGE_ONE:
                fragment = myFragment1;
                break;
            case good.PAGE_TWO:
                fragment = myFragment2;
                break;
            case good.PAGE_THREE:
                fragment = myFragment3;
                break;
            case good.PAGE_FOUR:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }


    //返回Item的数量
    @Override
    public int getItemCount() {
        return PAGER_COUNT;
    }


}

