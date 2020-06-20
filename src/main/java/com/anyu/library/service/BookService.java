package com.anyu.library.service;

import com.anyu.library.entity.Book;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BookService extends IService<Book> {

    List<Book> listBooks(String name, String author, int type);

    Boolean saveBook(Book book);

    Book getBook(String id, String name, String author);

    Boolean removeBook(String id);

    Boolean updateBook(Book book);

    Page<Book> listBook(Page<Book> page);

}
