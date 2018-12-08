package com.csu.etrainingsystem.security.shiro;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.springframework.http.HttpRequest;
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
        System.out.println ("自定义过滤器在工作");

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest res=(HttpServletRequest)req;
//        Cookie[] cookies=res.getCookies ();
//        Cookie cookie=new Cookie ();
//        cookie.setPath ("/");
    //    response.addCookie (cookie);
        if(res.getSession (false)==null){
            System.out.println("本次请求的session为空");
            System.out.println (res.getSession(true).getId ());
        }
        else {
            System.out.println ("本次请求的session是:"+res.getSession (false).getId ());

        }
        response.setHeader("Access-Control-Allow-Origin", res.getHeader ("Origin"));
        response.setHeader ("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
