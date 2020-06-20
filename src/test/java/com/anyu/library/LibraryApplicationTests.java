package com.anyu.library;

import com.anyu.library.entity.Book;
import com.anyu.library.entity.Master;
import com.anyu.library.mapper.MasterMapper;
import com.anyu.library.service.BookService;
import com.anyu.library.service.MasterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class LibraryApplicationTests {
    @Autowired
   private MasterMapper masterMapper;
    @Autowired
    private MasterService masterService;
    @Autowired
    private BookService bookService;

    @Test
    void master() {
        System.out.println(masterMapper.selectById(103616));

        Master master = new Master();
        master.setAccount("popo");
        master.setName("popo");
        master.setPassword("123");
        masterMapper.insert(master);
//        System.out.println(masterService.login("popo", "123"));

    }

    @Test
    void book() {
//        bookService.listBooks().forEach(System.out::println);
        for (int i = 1; i < 10; i++) {
            Book book = new Book();
            book.setName("社会心理学"+i);
            book.setAuthor("gcc"+i);
            book.setPress("demo");
            book.setType(5);
            book.setPrice(100.0);
            book.setPublishDate(LocalDate.of(2018,2,14));
            bookService.saveBook(book);
        }

    }
}
