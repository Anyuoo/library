package com.anyu.library.service;

import com.anyu.library.entity.Reader;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ReaderService extends IService<Reader> {
    Page<Reader> listReaders(Page<Reader> page);

    Reader getReader(String id, String account, String name);

    Boolean saveReader(Reader reader);
}
