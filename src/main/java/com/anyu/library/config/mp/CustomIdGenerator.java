package com.anyu.library.config.mp;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @Version 1.0
 * @Author Anyu
 * @Date  2020/6/17
 * @Desc mp 自定义id生成
 */

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final AtomicLong al = new AtomicLong(1);

    @Override
    public Number nextId(Object entity) {
        return al.getAndAdd(1);
    }

    @Override
    public String nextUUID(Object entity) {
        String[] names = entity.getClass().getName().split("\\.");
        String name = names[names.length - 1];
        String id =String.valueOf(UUID.randomUUID().getMostSignificantBits()).replace("-","").substring(0,8);
        return name+"-"+id;
    }
}
