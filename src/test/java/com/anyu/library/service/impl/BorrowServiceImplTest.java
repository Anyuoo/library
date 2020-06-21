package com.anyu.library.service.impl;

import com.anyu.library.entity.Book;
import com.anyu.library.entity.Borrow;
import com.anyu.library.entity.Reader;
import com.anyu.library.service.BookService;
import com.anyu.library.service.BorrowService;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BorrowServiceImplTest {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;


    @Test
    void saveOverdue() {
//        Borrow borrow = new Borrow();
//        borrow.setBookId("Book-1141648");
//        borrow.setReaderId("4");
//        borrowService.saveBorrow(borrow);

        for (Reader reader : readerService.list()) {
            for (Book book : bookService.list()) {
                Borrow borrow = new Borrow();
                borrow.setBookId(book.getId());
                borrow.setReaderId(reader.getId());
                borrowService.saveBorrow(borrow);
            }
        }
    }

    @Test
    void listOverdue() {
        Page<Borrow> page = new Page<>();
        borrowService.listOverdue(page);
        System.out.println(page);

    }
}