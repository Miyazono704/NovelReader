package com.example.NovelReader.bangdan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.NovelReader.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BookLeftAdapter(List<String> types) {
        super(R.layout.item_book_left, types);
    }

    /**
     * 设置item数据 ViewHolder listview滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
     */
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        // 第一个item默认选中状态
        if (baseViewHolder.getLayoutPosition() == 0) {
            baseViewHolder.setBackgroundResource(R.id.bookLeftType, R.color.bg_white);
        }
        baseViewHolder.setText(R.id.bookLeftType, s);
    }

}
