package com.anyu.library.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@TableName(value = "reader")
public class Reader {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String account;
    private String password;
    private String headImage;
    private String nickname;
    private int sex;
    private String studentId;
    private int borrowNumber;

    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime registerTime;

    private String name;
    @TableLogic
    private int status;
}
