package com.anyu.library.controller.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.anyu.library.annotation.RequiredLogin;
import com.anyu.library.entity.Master;
import com.anyu.library.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginStatusInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            RequiredLogin requiredLogin = ((HandlerMethod) handler).getMethodAnnotation(RequiredLogin.class);
//            if (requiredLogin != null && hostHolder.getMaster() == null) {
//                response.sendRedirect(request.getContextPath()+"/login");
//                System.out.println(request.getServletPath());
//                return false;
//            }
//        }
        if (hostHolder.getMaster() == null && !request.getServletPath().equals("/login")) {
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        return true;
    }
}
