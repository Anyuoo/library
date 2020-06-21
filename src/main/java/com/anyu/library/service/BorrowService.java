package com.anyu.library.service;

import com.anyu.library.entity.Borrow;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BorrowService extends IService<Borrow> {
    Page<Borrow> listOverdue(Page<Borrow> page);

    Boolean saveBorrow(Borrow borrow);

    Page<Borrow> listBorrows(Page<Borrow> page);
}
