package com.anyu.library.entity;
import lombok.Data;

@Data
public class OverdueVO {
    private Book book;
    private Reader reader;
    private Borrow borrow;
}
