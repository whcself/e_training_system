
package com.csu.etrainingsystem.security.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 * @author lenovo
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager")DefaultWebSecurityManager securityManager
	){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		SecurityUtils.setSecurityManager (securityManager);
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		//放行login.html页面
		filterMap.put("/csu-engineer-train-front/login.html", "anon");

		filterMap.put("/csu-engineer-train-front/manager/*", "authc");
		filterMap.put("/csu-engineer-train-front/teacher/*", "authc");
		filterMap.put("/csu-engineer-train-front/student/*", "authc");
		filterMap.put("/csu-engineer-train-front/**", "anon");
		filterMap.put("/login", "anon");
		filterMap.put("/abc", "anon");
		filterMap.put("/tlogin", "anon");
		//授权过滤器
		//注意：当前授权拦截后，shiro会自动跳转到未授权页面
		//filterMap.put("/admin/**", "perms[user:admin]");
		//filterMap.put("/teacher/**", "perms[user:teacher]");
		//管理员权限 user:admin
		//教师权限   user:teacher
		//教师权限中又包括加班权限/物料权限user:material/user:overwork
		//学生权限 user:student
		//perms参数可以多个，用逗号隔开
	//	filterMap.put("/material/decrMaterialNum", "perms[user:applymaterial]");
	//	filterMap.put("/purchase/addPurchase", "perms[user:purchasematerial]");
		//filterMap.put("/material/*", "perms[user:applymaterial]");
		//filterMap.put("/purchase/*", "perms[user:purchasematerial]");
		//filterMap.put("/purchase/*", "perms[user:purchasematerial]");

		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/swagger-resources/**", "anon");
		filterMap.put("/v2/**", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/**", "authc");

		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		//设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

		return shiroFilterFactoryBean;
	}
	
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(
			@Qualifier("userRealm")UserRealm userRealm,
			@Qualifier("sessionManager") SessionManager sessionManager){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
		//设置session管理器
		securityManager.setSessionManager (sessionManager);
		return securityManager;
	}
	@Bean("sessionDAO")
	public MemorySessionDAO getSessionDAO(){
		return new MemorySessionDAO ();
	}
	/**
	 * 创建Realm
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm(){
		return new UserRealm ();
	}
	
	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}

	@Bean("sessionManager")
	public SessionManager getSessionManager(@Qualifier("sessionDAO") MemorySessionDAO sessionDAO
			                                //         , @Qualifier("simpleCookie")SimpleCookie simpleCookie
	){

		DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager ();
		defaultWebSessionManager.setSessionDAO (sessionDAO);
		defaultWebSessionManager.setSessionValidationSchedulerEnabled (true);
		//设置过期时间半个小时
		defaultWebSessionManager.setGlobalSessionTimeout (1800000);
	return defaultWebSessionManager;
	}
	@Bean("simpleCookie")
	public SimpleCookie getSimpleCookie(){
		SimpleCookie simpleCookie=new SimpleCookie ();
		simpleCookie.setName ("SHIRO-COOKIE");
		simpleCookie.setPath ("/");

		simpleCookie.setMaxAge (600000);
		simpleCookie.setHttpOnly (true);
       return simpleCookie;
	}

	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
//			@Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager securityManager) {
//		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//		return authorizationAttributeSourceAdvisor;
//	}


}
