//package com.csu.etrainingsystem.security;
//
//import com.sun.deploy.net.HttpResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class SecurityInterceptor implements HandlerInterceptor {
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o)throws Exception{
//        HttpSession session=request.getSession();
//        if(session.getAttribute("username")==null){
//            response.sendError(response.SC_UNAUTHORIZED,"当前用户未登陆");
//            return false;
//        }
//        return true;
//    }
//}
