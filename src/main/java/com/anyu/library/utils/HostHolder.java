package com.anyu.library.utils;

import com.anyu.library.entity.Master;
import org.springframework.stereotype.Component;


/**
 * @Version 1.0
 * @Author Anyu
 * @Date 2020/6/19
 * @Desc 储存登录的master
 */
@Component
public class HostHolder {
    private static final ThreadLocal<Master> tl = new ThreadLocal<>();

    public void setMaster(Master master) {
        tl.set(master);
    }

    public Master getMaster() {
        return tl.get();
    }

    public void removeMaster() {
        tl.remove();
    }
}
