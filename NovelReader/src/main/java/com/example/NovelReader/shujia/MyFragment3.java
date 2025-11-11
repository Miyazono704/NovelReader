package com.example.NovelReader.shujia;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.NovelReader.MyApplication;
import com.example.NovelReader.R;
import com.example.NovelReader.Tips;
import com.example.NovelReader.date.DetailActivity;
import com.example.NovelReader.javabean.Book;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFragment3 extends Fragment {


    @BindView(R.id.placeRecyclerView)
    RecyclerView orderRecyclerView;

    PlaceOrderAdapter orderAdapter;

    public MyFragment3() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shujia,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 首页瀑布流列表 网格布局3*3 当我们的设置为VERTICAL时，很容易发现当上一行的高度排放相等时它一行排放的先后顺序是从左到右。
        orderRecyclerView.setLayoutManager(new StaggeredGridLayoutManager( 3,StaggeredGridLayoutManager.VERTICAL));
        initOrderAdapter();
    }
    /**
     * 初始化书架列表适配器
     */
    private void initOrderAdapter() {
        // 实例化书架列表适配器对象
        orderAdapter = new PlaceOrderAdapter(MyApplication.getCartBooks());

        // 设置空布局
        orderAdapter.setEmptyView(getEmptyView());

        // 设置动画效果
        orderAdapter.setAnimationEnable(true);
//        orderAdapter.setAnimationFirstOnly(false);
        orderAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);

        // 点击item事件触发
        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Book book = (Book) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
        //长按删除
        orderAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            //返回false，长按事件后，继续短按事件
            //返回true，仅长按事件
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("是否删除本书")
                        .setPositiveButton("确定", (dialog, which) -> {
                            // 清空数据
                            MyApplication.getCartBooks().remove(position);
                            // 通知适配器数据变化
                            orderAdapter.notifyDataSetChanged();
                            Tips.show("已删除");
                        })
                        .setNegativeButton("取消", (dialog, which)-> {

                        })
                        .create()
                        .show();
                return true;
            }
        });


        // 设置适配器
        orderRecyclerView.setAdapter(orderAdapter);
        }
        /**
         * 书架空布局
         */

        private View getEmptyView(){
            return getLayoutInflater().inflate(R.layout.empty_book_view, orderRecyclerView, false);
        }

}
