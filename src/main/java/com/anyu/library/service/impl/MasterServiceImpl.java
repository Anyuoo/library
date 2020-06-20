package com.anyu.library.service.impl;

import com.anyu.library.entity.LoginTicket;
import com.anyu.library.entity.Master;
import com.anyu.library.mapper.MasterMapper;
import com.anyu.library.service.MasterService;
import com.anyu.library.utils.RedisKeyUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MasterServiceImpl extends ServiceImpl<MasterMapper, Master> implements MasterService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Master getMaster(Long id) {
        return this.getById(id);
    }

    @Override
    public void logout(String ticket) {
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        redisTemplate.delete(ticketKey);
    }

    @Override
    public Map<String, Object> login(String account, String password, int expiredSeconds) {
        QueryWrapper<Master> wrapper = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>(1);

        wrapper.eq("account", account)
                .eq("password", password)
                .eq("status", 0);

        this.list(wrapper);
        Master master = this.getOne(wrapper);
        if (master == null) {
            map.put("msg", "用户不存在！");
            return map;
        }
        LoginTicket loginTicket = new LoginTicket();
        String ticket = UUID.randomUUID().toString();
        loginTicket.setTicket(ticket);
        loginTicket.setMasterId(master.getId());
        loginTicket.setLastLoginTime(new Date());
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        redisTemplate.opsForValue().set(ticketKey, loginTicket);

        map.put("ticket", ticket);
        // if master is exist,return true
        return map;
    }
}
