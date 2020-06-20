package com.anyu.library.config.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Version 1.0
 * @Author Anyu
 * @Date  2020/6/16
 * @Desc mybatis-plus 自动填充字段配置
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"registerTime", LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"entryTime", LocalDateTime.class,LocalDateTime.now());
    }
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
