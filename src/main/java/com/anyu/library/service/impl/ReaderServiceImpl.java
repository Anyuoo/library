package com.anyu.library.service.impl;

import com.anyu.library.entity.Reader;
import com.anyu.library.mapper.ReaderMapper;
import com.anyu.library.service.ReaderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderServiceImpl extends ServiceImpl<ReaderMapper,Reader> implements ReaderService  {

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Boolean saveReader(Reader reader) {
        if (reader == null) {
            return false;
        }
        if (getReader(null, reader.getAccount(), null) != null || getReader(null, null, reader.getName()) != null) {
            return false;
        }
        return this.save(reader);
    }

    @Override
    public Boolean updateReader(Reader reader) {
        return this.updateById(reader);
    }

    @Override
    public Page<Reader> listReaders(Page<Reader> page) {
        return this.page(page);
    }

    @Override
    public Reader getReader(String id, String account, String name) {
        QueryWrapper<Reader> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (account != null) {
            wrapper.eq("account", account);
        }
        if (name != null) {
            wrapper.eq("name", name);
        }
        return this.getOne(wrapper);
    }

}
