package com.anyu.library.service.impl;

import com.anyu.library.entity.Book;
import com.anyu.library.entity.Borrow;
import com.anyu.library.entity.Reader;
import com.anyu.library.mapper.BorrowMapper;
import com.anyu.library.service.BookService;
import com.anyu.library.service.BorrowService;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;

    @Override
    public Page<Borrow> listOverdue(Page<Borrow> page) {
        return this.page(page, wrapper(0,0,true));
    }

    @Override
    public Page<Borrow> listBorrows(Page<Borrow> page) {
        return this.page(page,wrapper(0,null,false));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Boolean saveBorrow(Borrow borrow) {
        int num;
        Reader reader = readerService.getReader(borrow.getReaderId(), null, null);
        reader.setBorrowNumber(reader.getBorrowNumber()+1);
        readerService.updateReader(reader);

        Book book = bookService.getBook(borrow.getBookId(), null, null);
        if ((num = book.getBorrowNum()) > 0) {
            book.setBorrowNum(num-1);
            bookService.updateBook(book);
            return this.saveOrUpdate(borrow);
        }
        return false;
    }

    /**
     *
     * @param status 借阅记录状态
     * @param returnStatus 是否归还
     * @param SRTime 应该归还时间
     * @return
     */
    private QueryWrapper<Borrow> wrapper(Integer status,Integer returnStatus, Boolean SRTime) {
        QueryWrapper<Borrow> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status",status );
        }
        if (returnStatus != null) {
            wrapper.eq("return_status", returnStatus);
        }
        if (SRTime) {
            wrapper.lt("should_r_time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return wrapper;
    }
}
