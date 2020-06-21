package com.anyu.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.anyu.library.annotation.RequiredLogin;
import com.anyu.library.entity.Book;
import com.anyu.library.service.BookService;
import com.anyu.library.utils.LibraryConstant;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")

public class BookController implements LibraryConstant {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String listBooks(Page<Book> page) {
        page.setSize(20);
        bookService.listBooks(page,null,null,BookType.ALL.Index());
        return "list";
    }

    @GetMapping("/add")
    @RequiredLogin
    public String getAddBookPage(Book book) {
        return "add";
    }

    @PostMapping("/add")
    public String addBook(Book book, Model model) {
        if (book == null || StringUtils.isBlank(book.getAuthor()) || StringUtils.isBlank(book.getName())) {
            model.addAttribute("msg","书籍信息不能为空！");
            return "add";
        }
        if (!bookService.saveBook(book)) {
            model.addAttribute("msg", 0);
        }else {
            model.addAttribute("msg", 1);
        }
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    @RequiredLogin
    public String getEditPage(@PathVariable("id") String id,Model model) {
        Book book = bookService.getBook(id,null,null);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String editBookInfo(Book book) {
        if (!bookService.updateBook(book)) {
            return "edit";
        }
        return "redirect:/book/list";
    }

    @PostMapping("/delete")
    @ResponseBody
    @RequiredLogin
    public String delBook(String id) {
        JSONObject jsonObject = new JSONObject();
        if (!bookService.removeBook(id)) {
            jsonObject.put("msg", 2);
        }else {
            jsonObject.put("msg", 1);
        }
        return jsonObject.toJSONString();
    }
}
