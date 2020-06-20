package com.anyu.library.service.impl;

import com.anyu.library.entity.Book;
import com.anyu.library.mapper.BooksMapper;
import com.anyu.library.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BooksMapper, Book> implements BookService {


    @Override
    public List<Book> listBooks(String name, String author, int type) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.eq("name", name);
        }
        if (author != null) {
            wrapper.eq("author", author);
        }
        if (type != 0) {
            wrapper.eq("type", type);
        }
        wrapper.eq("status", 0)
                .orderByDesc("register_time");
        return this.list(wrapper);
    }

    @Override
    public Boolean saveBook(Book book) {
        //书籍存在,添加失败
        if(this.getBook(null,book.getName(),book.getAuthor()) != null){
            return false;
        }
        return this.save(book);
    }

    @Override
    public Book getBook(String id, String name, String author) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (name != null) {
            wrapper.eq("name", name);
        }
        if (author != null) {
            wrapper.eq("author", author);
        }
        return this.getOne(wrapper);
    }

    @Override
    public Boolean removeBook(String id) {
        return this.removeById(id);
    }

    @Override
    public Boolean updateBook(Book book) {
        List<Book> books = this.listBooks(book.getName(), book.getAuthor(), 0);
        if (books.size() > 1) {
            return false;
        }
        return this.updateById(book);
    }

    @Override
    public Page<Book> listBook(Page<Book> page) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0)
                .orderByDesc("register_time");
        return this.page(page,wrapper);
    }


}
