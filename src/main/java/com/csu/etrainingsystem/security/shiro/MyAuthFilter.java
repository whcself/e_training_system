//package com.csu.etrainingsystem.security.shiro;
//
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.apache.shiro.web.filter.authc.UserFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.thymeleaf.util.StringUtils;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class MyAuthFilter extends UserFilter//  FormAuthenticationFilter
//{
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            setHeader(httpRequest,httpResponse);
//            return true;
//        }
//        return super.preHandle(request,response);
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
//        if (!allowed) {
//            // 判断请求是否是options请求
//            String method = WebUtils.toHttp(request).getMethod();
//            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
//                HttpServletResponse httpResponse = (HttpServletResponse) response;
//                HttpServletRequest httpRequest = (HttpServletRequest) request;
//                setHeader(httpRequest,httpResponse);
//                return true;
//            }
////            if (isLoginRequest(request, response)) {
////                return true;
////            } else {
////                Subject subject = getSubject(request, response);
////                // If principal is not null, then the user is known and should be allowed access.
////                System.out.println ("返回的subject是"+subject.toString ()+ subject.getPrincipal() != null);
////                return subject.getPrincipal() != null;
////            }
//        }
//        return allowed;
//    }
//
//
//
//    /**
//     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
//     * 因此重写改成传输JSON数据
//     */
//    @Override
//    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        saveRequest(request);
//        setHeader((HttpServletRequest) request,(HttpServletResponse) response);
//        PrintWriter out = response.getWriter();
//        out.println("验证失败");
//        out.flush();
//        out.close();
//    }
//
//    /**
//     * 为response设置header，实现跨域
//     */
//    private void setHeader(HttpServletRequest request,HttpServletResponse response){
//        //跨域的header设置
//        System.out.println ("进行跨域设置"+request.getHeader("Origin"));
//   //     response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-control-Allow-Origin", request.getHeader("*"));
//        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
//        //防止乱码，适用于传输JSON数据
//        response.setHeader("Content-Type","application/json;charset=UTF-8");
//        response.setStatus(HttpStatus.OK.value());
//    }
//
//}
