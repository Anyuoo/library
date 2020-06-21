package com.anyu.library.controller;

import com.anyu.library.entity.Reader;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/list")
    public String listReader(Page<Reader> page) {
        page.setSize(20);
        readerService.listReaders(page);
        return "reader/list";
    }
}
