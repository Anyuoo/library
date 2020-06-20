package com.anyu.library.controller;

import com.anyu.library.service.MasterService;
import com.anyu.library.utils.LibraryConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class LoginController implements LibraryConstant {
    @Autowired
    private MasterService masterService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 跳转登录页面
     * @return 登录页面
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    /**
     * 登录表单
     * @param account 账户
     * @param password 密码
     * @param model
     * @param response
     * @return 登录成功或失败页面
     */
    @PostMapping("/login")
    public String Login(String account, String password, Model model, HttpServletResponse response) {
        Map<String, Object> loginMsg = masterService.login(account, password,DEFAULT_EXPIRED_SECOND);

        if (!loginMsg.containsKey("ticket")) {
            model.addAttribute("account", account);
            model.addAttribute("password", password);
            return "login";
        }

        //发送登录凭证给客户端
        Cookie cookie = new Cookie("ticket",loginMsg.get("ticket").toString());
        cookie.setMaxAge(DEFAULT_EXPIRED_SECOND);
        cookie.setPath(contextPath);
        response.addCookie(cookie);

        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        masterService.logout(ticket);
        return "redirect:/login";
    }
}
