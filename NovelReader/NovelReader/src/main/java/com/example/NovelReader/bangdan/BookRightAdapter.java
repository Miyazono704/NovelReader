package com.example.NovelReader.bangdan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.NovelReader.R;
import com.example.NovelReader.javabean.Book;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookRightAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

    public BookRightAdapter(List<Book> books) {
        super(R.layout.item_book_right, books);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Book book) {
        baseViewHolder.setImageResource(R.id.bookRightImage, book.getImage())
                .setText(R.id.bookRightName, book.getName())
                .setText(R.id.bookRightPrice, book.getPrice()+" 万热度");
    }
}
