package com.anyu.library.utils;

/**
 * @Version 1.0
 * @Author Anyu
 * @Date 2020/6/19
 * @Desc 生成redis键
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String TICKET = "ticket";

    /**
     * 登录凭证
     * @param ticket 凭证
     * @return 键
     */
    public static String getTicketKey(String ticket) {
        return TICKET + SPLIT + ticket;
    }
}
