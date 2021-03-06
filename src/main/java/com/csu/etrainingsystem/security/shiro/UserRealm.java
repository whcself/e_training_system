package com.csu.etrainingsystem.security.shiro;


import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.security.Role;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 执行授权逻辑
     */
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println ("执行授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo ();
        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject ();
        User u = (User) subject.getPrincipal ();
        User user = this.userService.getUser (u.getAccount ());
        //根据不同的角色授予不同权限,管理员权限才能打开管理员界面,老师权限才能打开老师界面;
        if (user.getRole ().equals ("admin")) {
             //5种权限,加到对应的方法上面
            //然后这里做移位判断,分别授予几种权限,就得了
            info.addStringPermission ("user:admin");
            info.addStringPermission ("material:MATERIAL_REGISTER");
            info.addStringPermission ("material:MATERIAL_BUY");
            info.addStringPermission ("material:MATERIAL_BUY_V");
            info.addStringPermission ("material:MATERIAL_PURCHASE");
            info.addStringPermission ("material:REMI_V");
            info.addStringPermission ("material:SAVE");
            info.addStringPermission("overwork");

        } else if (user.getRole ().equals ("student")) {
            info.addStringPermission ("user:student");
        } else {
            Teacher teacher = teacherService.getTeacher (user.getAccount ());
            if (teacher != null) {
                info.addStringPermission ("user:teacher");
              Integer privilege= teacher.getMaterial_privilege ();
              Integer overwork=teacher.getOvertime_privilege();
              if(overwork==1)info.addStringPermission("overwork");
              String[] pvls= com.csu.etrainingsystem.util.StringUtils.toBinary (privilege);
              if (Integer.parseInt (pvls[0])==1){
                  info.addStringPermission ("material:SAVE");
              }
                if (Integer.parseInt (pvls[1])==1){
                    info.addStringPermission ("material:REMI_V");
                }
                if (Integer.parseInt (pvls[2])==1){
                    info.addStringPermission ("material:MATERIAL_PURCHASE");
                }
                if (Integer.parseInt (pvls[3])==1){
                    info.addStringPermission ("material:MATERIAL_BUY_V");
                }
                if (Integer.parseInt (pvls[4])==1){
                    info.addStringPermission ("material:MATERIAL_BUY");
                }
                if (Integer.parseInt (pvls[5])==1){
                    info.addStringPermission ("material:MATERIAL_REGISTER");
                }
            }
        }

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println ("执行认证逻辑");

        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println ("登录的密码是"+token.getPassword ().toString ());
        User user = userService.getUser (token.getUsername ());
        //如果不能存在就创建
        Subject subject = SecurityUtils.getSubject ();
        if (user == null) {
            //用户不存在
            return null;//shiro底层会抛出UnKnowAccountException
        }

        /**
         * 防止重复登录,踢掉之前登录的用户
         */
        String userName = (String) authenticationToken.getPrincipal ();
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager ();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager ();
        Collection<Session> sessions = sessionManager.getSessionDAO ().getActiveSessions ();//获取当前已登录的用户session列表

        for (Session session : sessions) {
            Object obj = session.getAttribute (DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            if (obj instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                obj = spc.getPrimaryPrincipal ();
                if (obj instanceof User) {
                    User u = (User) obj;
                    if (userName.equals (u.getAccount ()) && u.getPwd ().equals (user.getPwd ())) {
                        //删除之前的session
                        sessionManager.getSessionDAO ().delete (session);
                        //throw new ConcurrentAccessException("重复登录");
                        //
                    }
                }
            }

        }
        //最后通过密码判断e-training-system
        return new SimpleAuthenticationInfo (user, user.getPwd (),"");
    }

}
