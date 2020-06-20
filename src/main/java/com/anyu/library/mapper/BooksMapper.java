package com.anyu.library.mapper;

import com.anyu.library.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BooksMapper extends BaseMapper<Book> {
}
