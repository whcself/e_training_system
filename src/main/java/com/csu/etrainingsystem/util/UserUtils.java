package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.user.entity.User;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.servlet.http.HttpSession;

public class UserUtils {
    public static User getHttpSessionUser(HttpSession session){
        SimplePrincipalCollection simplePrincipalCollection=(SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        User user=(User)simplePrincipalCollection.getPrimaryPrincipal ();
        return user;
    }
}
