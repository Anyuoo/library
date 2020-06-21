package com.anyu.library.controller;

import com.anyu.library.entity.Book;
import com.anyu.library.entity.Borrow;
import com.anyu.library.entity.OverdoVO;
import com.anyu.library.service.BookService;
import com.anyu.library.service.BorrowService;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;

    @GetMapping("/list")
    public String listBorrow(Page<Borrow> page) {
        page.setSize(20);
        borrowService.listBorrows(page);
        return "borrow/list";
    }

    @GetMapping("/pass")
    public String passReader(Page<OverdoVO> page) {
        page.setSize(20);

        Page<Borrow> borrowPage = new Page<>();
        borrowPage.setSize(20);
        borrowService.listOverdue(borrowPage);

        List<OrderItem> orders = borrowPage.getOrders();
        if (!orders.isEmpty()){
            List<OverdoVO> list = new ArrayList<>(orders.size());
            borrowPage.getRecords().forEach(
                    borrow ->{
                        OverdoVO overdoVO = new OverdoVO();
                        overdoVO.setBook(bookService.getBook(borrow.getBookId(), null, null));
                        overdoVO.setReader(readerService.getReader(borrow.getReaderId(), null, null));
                        overdoVO.setBorrow(borrow);
                        list.add(overdoVO);
                    }
            );
            page.setRecords(list);
        }
        return "borrow/pass";
    }
}
