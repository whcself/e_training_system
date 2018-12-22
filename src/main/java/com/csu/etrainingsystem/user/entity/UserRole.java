package com.csu.etrainingsystem.user.entity;

//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.servlet.http.HttpSession;

public class UserRole {
    public static String TEACHER = "teacher";
    public static String STUDENT = "student";
    public static String ADMIN = "admin";
    public static String SCJN = "scjn";
    public static String WHCSELF = "whcself";

    public static boolean hasRole(User user, String role) {
        return user.getRole().equals(role) || user.getRole().equals(SCJN) || user.getRole().equals(WHCSELF);
    }

    public static User getUser(HttpSession session) {
       // SimplePrincipalCollection spc = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
       // return (User) spc.getPrimaryPrincipal();
        return null;
    }
}
