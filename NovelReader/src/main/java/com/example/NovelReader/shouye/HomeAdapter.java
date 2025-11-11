package com.example.NovelReader.shouye;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.NovelReader.R;
import com.example.NovelReader.javabean.Book;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<Book,BaseViewHolder> {
    private int i=1;//排名
    public HomeAdapter(List<Book> books) {
        super(R.layout.item_home_snack, books);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Book book) {

        baseViewHolder.setImageResource(R.id.homeRightImage, book.getImage())
                .setText(R.id.homeRightName, book.getName())
                .setText(R.id.homeRightPrice,   book.getPrice()+" 万热度")
                .setText(R.id.homeRight, i++ +"");

    }
}
