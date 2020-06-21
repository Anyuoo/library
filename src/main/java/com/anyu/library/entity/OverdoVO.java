package com.anyu.library.entity;
import lombok.Data;

@Data
public class OverdoVO {
    private Book book;
    private Reader reader;
    private Borrow borrow;
}
