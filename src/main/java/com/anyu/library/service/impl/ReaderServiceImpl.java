package com.anyu.library.service.impl;

import com.anyu.library.entity.Reader;
import com.anyu.library.mapper.ReaderMapper;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl extends ServiceImpl<ReaderMapper,Reader> implements ReaderService  {
    @Override
    public Page<Reader> listReaders(Page<Reader> page) {
        return this.page(page);
    }

    @Override
    public Reader getReader(String id, String account) {
        QueryWrapper<Reader> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (account != null) {
            wrapper.eq("account", account);
        }
        return this.getOne(wrapper);
    }

}
