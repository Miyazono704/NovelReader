package com.example.NovelReader.javabean;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 图书类
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图书名称
     */
    private String name;

    /**
     * 图书单价
     */
    private double price;

    /**
     * 图书图片资源
     */
    private int image;

    /**
     * 图书详情
     */
    private String detail;



    public Book() {
    }

    public Book(String name, double price, int image, String detail) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    /**
     * 重写equals方法 用于比较图书是否已添加到书架
     *
     * @param o 待比较图书对象
     * @return 比较结果
     */
   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @NotNull
    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image +
                ", detail='" + detail + '\'' +
                '}';
    }
}
