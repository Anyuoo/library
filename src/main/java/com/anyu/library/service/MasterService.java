package com.anyu.library.service;

import com.anyu.library.entity.Master;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Version 1.0
 * @Author Anyu
 * @Date 2020/6/16
 * @Desc
 */
public interface MasterService extends IService<Master> {
    //    Boolean saveMaster();
    Map<String, Object> login(String account, String password, int expiredSeconds);

    Master getMaster(Long id);

    void logout(String ticket);

}
