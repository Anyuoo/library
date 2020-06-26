package com.anyu.library.controller.interceptor;

import com.anyu.library.entity.LoginTicket;
import com.anyu.library.entity.Master;
import com.anyu.library.service.MasterService;
import com.anyu.library.utils.CookieUtil;
import com.anyu.library.utils.HostHolder;
import com.anyu.library.utils.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Version 1.0
 * @Author Anyu
 * @Date 2020/6/19
 * @Desc 登录拦截器，将登录用户注入
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MasterService masterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //得到浏览器传入的凭证
        String ticket = "ticket";
        ticket = CookieUtil.getCookieValue(request, ticket);
        if (StringUtils.isBlank(ticket)) {
            return true;
        }

        //验证凭证是否有效
        ticket = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket lt = (LoginTicket)redisTemplate.opsForValue().get(ticket);
        if (lt != null && lt.getExpired().after(new Date())) {
            Master master = masterService.getMaster(lt.getMasterId());
            hostHolder.setMaster(master);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Master master = hostHolder.getMaster();
        if (master != null && modelAndView != null) {
            modelAndView.addObject("loginMaster",master);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.removeMaster();
    }

}
