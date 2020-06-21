package com.anyu.library.service.impl;

import com.anyu.library.entity.Book;
import com.anyu.library.mapper.BooksMapper;
import com.anyu.library.service.BookService;
import com.anyu.library.utils.LibraryConstant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BooksMapper, Book> implements BookService, LibraryConstant {


    @Override
    public List<Book> listBooks(String name, String author) {
        return this.list(listWrapper(name, author, BookType.ALL.Index()));
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
        return this.getOne(oneWrapper(id,name,author));
    }

    @Override
    public Boolean removeBook(String id) {
        return this.removeById(id);
    }

    @Override
    public Boolean updateBook(Book book) {
        List<Book> books = this.listBooks(book.getName(), book.getAuthor());
        if (books.size() > 1) {
            return false;
        }
        return this.updateById(book);
    }

    @Override
    public Page<Book> listBooks(Page<Book> page, String name, String author, int type) {
        return this.page(page,listWrapper(name, author, type));
    }


    /**
     * 多条图书信息
     * @param name
     * @param author
     * @param type
     * @return
     */
    private QueryWrapper<Book> listWrapper(String name, String author, int type){
        return wrapper(null, name, author, type,ORDER_DESC,"register_time",STATUS_NORMAL);
    }

    /**
     * 单体图书信息查询条件构造
     * @param id id
     * @param name name
     * @param author author
     * @return wrapper
     */
    private QueryWrapper<Book> oneWrapper(String id, String name, String author){
        return wrapper(id, name, author, BookType.ALL.Index(), ORDER_NONE,null,STATUS_NORMAL);
    }

    private QueryWrapper<Book> wrapper(String id, String name, String author, int type, int order, String orderCol,int status) {
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

        if (type != BookType.ALL.Index()) {
            wrapper.eq("type", type);
        }

        if (order != ORDER_NONE || orderCol == null) {
            if (order == ORDER_DESC) {
                wrapper.orderByDesc(orderCol);
            }else if (order == ORDER_ASC) {
                wrapper.orderByAsc(orderCol);
            }else {
                wrapper.orderByDesc(orderCol);
            }
        }

        wrapper.eq("status", status);

        return wrapper;
    }


}
