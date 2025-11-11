package com.example.NovelReader.bangdan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.NovelReader.date.DataServer;
import com.example.NovelReader.date.DetailActivity;
import com.example.NovelReader.MyApplication;
import com.example.NovelReader.R;
import com.example.NovelReader.Tips;
import com.example.NovelReader.javabean.Book;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyFragment2 extends Fragment {



    // 页面左边列表已选择的Position
    private int leftSelectPosition = 0;

    @BindView(R.id.bookLeftRecyclerView)
    RecyclerView leftRecyclerview;

    @BindView(R.id.bookRightRecyclerView)
    RecyclerView rightRecyclerView;
    // 右边适配器
    private BookRightAdapter rightAdapter;



    public  MyFragment2 newInstance(){
        return new MyFragment2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
//在fragment中this需要getActivity,注意点才起作用


        View view = inflater.inflate(R.layout.bangdan,container,false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        leftRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initLeftAdapter();
        initRightAdapter();
    }
    /**
     * 初始化左边适配器
     */
    @SuppressLint("ResourceAsColor")
    private void initLeftAdapter() {
        // 实例化左边适配器对象
        BookLeftAdapter leftAdapter = new BookLeftAdapter(DataServer.getBookOrderList());


        // 触发点击按钮
        leftAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position != leftSelectPosition) {
                String item = (String) adapter.getItem(position);

                // 原本选中的item变成未选中颜色
                Objects.requireNonNull(adapter.getViewByPosition(leftSelectPosition, R.id.bookLeftType)).setBackgroundResource(R.color.colorContent);
                // 当前item变成选中颜色
                Objects.requireNonNull(adapter.getViewByPosition(position, R.id.bookLeftType)).setBackgroundResource(R.color.bg_white);
                leftSelectPosition = position;

                // 刷新右边列表
                // 设置动画效果
                //开启动画
                rightAdapter.setAnimationEnable(true);
                //是否只执行一次动画
                rightAdapter.setAnimationFirstOnly(false);
                //设置动画类型
                rightAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);

                switch (position) {
                    case 1:
                        rightAdapter.setNewInstance(DataServer.getWanbenList());
                        break;
                    case 2:
                        rightAdapter.setNewInstance(DataServer.getKoubeiList());
                        break;
                    case 3:
                        rightAdapter.setNewInstance(DataServer.getYueduList());
                        break;
                    case  4:
                        rightAdapter.setNewInstance(DataServer.getGaofenList());
                        break;
                    default:
                        rightAdapter.setNewInstance(DataServer.getTuijianList());
                        break;
                }
            }
        });

        // 设置左边列表适配器
        leftRecyclerview.setAdapter(leftAdapter);
    }
    /**
     * 初始化右边适配器
     */
    public void initRightAdapter() {
        // 实例化右边适配器对象
        rightAdapter = new BookRightAdapter(DataServer.getTuijianList());

        // 设置尾部
        rightAdapter.addFooterView(getFooterView());

        // 点击item事件
        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Book book = (Book) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

        // 右边列表加入书架点击事件
        rightAdapter.addChildClickViewIds(R.id.bookRightAddBtn);
        rightAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Book book = (Book) adapter.getItem(position);
            if (view.getId() == R.id.bookRightAddBtn) {
                //判断字符串中是否有子字符串。如果有则返回true,如果没有则返回false。
                if (!MyApplication.getCartBooks().contains(book)) {
                    // 添加到书库

                    MyApplication.getCartBooks().add(book);
                    Tips.show("已添加" + book.getName() + "到书架");
                } else {
                    Tips.show("已在书架中，不能重复添加");
                }
            }
        });

        // 设置右边列表适配器
        rightRecyclerView.setAdapter(rightAdapter);
    }

    /**
     * 页面右边RecyclerView尾部View
     */
    private View getFooterView() {
        return getLayoutInflater().inflate(R.layout.footer_no_item, rightRecyclerView, false);
    }
}
