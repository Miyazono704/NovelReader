package com.example.NovelReader.shouye;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.NovelReader.MyAnimation3;
import com.example.NovelReader.R;
import com.example.NovelReader.Tips;
import com.example.NovelReader.date.DataServer;
import com.example.NovelReader.date.DetailActivity;
import com.example.NovelReader.javabean.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFragment1 extends Fragment {

    @BindView(R.id.edinput)
    EditText query;

    @BindView(R.id.tv_refresh1)
    TextView check;

    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;

    public MyFragment1() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shouye, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 首页瀑布流列表 网格布局2*4 当我们的设置为VERTICAL时，很容易发现当上一行的高度排放相等时它一行排放的先后顺序是从左到右。
        homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL));
        //LayoutManager的子类StaggeredGridLayoutManager
        initHomeAdapter();
        //搜索栏
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= query.getText().toString();
                if(name.equals("开局地摊卖大力")){
                    SearchAdapter(DataServer.HomeList());
                }
                else if(name.equals("第九特区")){
                    SearchAdapter(DataServer.HomeList1());
                }
                else if(name.equals("凡骨")){
                   SearchAdapter(DataServer.HomeList2());
                }
                else if(name.equals("")){
                    initHomeAdapter();
                }else{
                    Tips.show("未找到该资源！");
                }
            }
        });
    }
    //首页
    private void initHomeAdapter() {
        // 实例化列表适配器对象
        HomeAdapter adapter = new HomeAdapter(DataServer.getHomeList());
        // 设置动画效果
        adapter.setAnimationEnable(true);

        adapter.setAdapterAnimation(new MyAnimation3());

        // 设置尾部，但是又导致刷新显示不出来的问题
        /*adapter.setFooterView(getFooterView(), 1);*/

        // 点击事件监听器
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Book book = (Book) adapter1.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("book", book);
            startActivity(intent);
        });
        // 设置适配器
        homeRecyclerView.setAdapter(adapter);

    }
    //搜索
    private void SearchAdapter(List<Book> books) {
        // 实例化列表适配器对象
        HomeAdapter adapter = new HomeAdapter(books);
        // 设置动画效果
        adapter.setAnimationEnable(true);
        adapter.setAdapterAnimation(new MyAnimation3());

        // 点击事件监听器
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Book book = (Book) adapter1.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("book", book);
            startActivity(intent);
        });
        // 设置适配器
        homeRecyclerView.setAdapter(adapter);
    }
}
