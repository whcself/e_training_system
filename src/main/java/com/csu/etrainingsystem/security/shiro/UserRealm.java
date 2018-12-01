package com.csu.etrainingsystem.security.shiro;



import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * 自定义Realm
 * @author lenovo
 *
 */
public class UserRealm extends AuthorizingRealm{

	/**
	 * 执行授权逻辑
	 */
	@Autowired
	private UserService userSerivce;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权逻辑");

		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		//添加资源的授权字符串
		//info.addStringPermission("user:add");

		//到数据库查询当前登录用户的授权字符串
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		//根据不同的角色授予不同权限,管理员权限才能打开管理员界面,老师权限才能打开老师界面;
		User dbUser = userSerivce.getUser (user.getAccount ());
		info.addStringPermission("user:admin");

		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("执行认证逻辑");

		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

		User user = userSerivce.getUser (token.getUsername ());
		Subject subject = SecurityUtils.getSubject();

		if(user==null){
			//用户不存在
			return null;//shiro底层会抛出UnKnowAccountException
		}

		/**
		 * 防止重复登录,踢掉之前登录的用户
		 */
		String userName = (String)authenticationToken.getPrincipal();
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表

		for(Session session:sessions){
	       Object obj=session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            if(obj instanceof SimplePrincipalCollection){
				SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
				obj = spc.getPrimaryPrincipal();
				if(obj instanceof User){
					User u=(User)obj;
					if(userName.equals(u.getAccount ())&&u.getPwd ().equals (user.getPwd ())) {
							sessionManager.getSessionDAO().delete(session);

							throw new ConcurrentAccessException("重复登录");
					//
					}
				}
			}

		}

		return new SimpleAuthenticationInfo(user,user.getPwd (),"");
	}

}
