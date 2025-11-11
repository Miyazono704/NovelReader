package com.example.NovelReader.shujia;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.NovelReader.R;
import com.example.NovelReader.javabean.Book;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 书架列表适配器
 */
public class PlaceOrderAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

    public PlaceOrderAdapter(List<Book> books) {
        super(R.layout.item_place_order, books);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Book book) {
        baseViewHolder.setImageResource(R.id.placeOrderImage, book.getImage())
                .setText(R.id.placeOrderName, book.getName());

    }
}
