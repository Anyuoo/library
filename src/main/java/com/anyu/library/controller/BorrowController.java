package com.anyu.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.anyu.library.entity.Borrow;
import com.anyu.library.entity.OverdueVO;
import com.anyu.library.service.BookService;
import com.anyu.library.service.BorrowService;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String passReader(Page<OverdueVO> page) {
        page.setSize(20);

        Page<Borrow> borrowPage = new Page<>();
        borrowPage.setSize(page.getSize());
        borrowService.listOverdue(borrowPage);

        List<Borrow> records = borrowPage.getRecords();
        if (!records.isEmpty()) {
            List<OverdueVO> list = new ArrayList<>(records.size());
            borrowPage.getRecords().forEach(
                    borrow -> {
                        OverdueVO overdueVO = new OverdueVO();
                        overdueVO.setBook(bookService.getBook(borrow.getBookId(), null, null));
                        overdueVO.setReader(readerService.getReader(borrow.getReaderId(), null, null));
                        overdueVO.setBorrow(borrow);
                        list.add(overdueVO);
                    }
            );
            page.setRecords(list);
        }
        return "borrow/pass";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String delBorrow(@PathVariable("id") Integer id) {
        JSONObject msg = new JSONObject();
        if (borrowService.removeBorrow(id)) {
            msg.put("msg", 1);
        }
        return msg.toJSONString();
    }

}
