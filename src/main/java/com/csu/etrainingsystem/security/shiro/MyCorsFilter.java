package com.csu.etrainingsystem.security.shiro;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyCorsFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
       // System.out.println ("自定义过滤器在工作");
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest res=(HttpServletRequest)req;
        System.out.println ("进行跨域色设置"+res.getHeader("Origin"));
        //     response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-control-Allow-Origin", res.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", res.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", res.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
