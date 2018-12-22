
package com.csu.etrainingsystem.security.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import com.csu.etrainingsystem.security.MySessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
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
//		Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
//		filtersMap.put("authc",new MyAuthFilter());
//		shiroFilterFactoryBean.setFilters(filtersMap);
//        //设置session管理器
//		securityManager.setSessionManager (sessionManager);
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
		/*filterMap.put("/add", "authc");
		filterMap.put("/update", "authc");*/
		//放行login.html页面
		filterMap.put("/login", "anon");
		//授权过滤器
		//注意：当前授权拦截后，shiro会自动跳转到未授权页面
	//	filterMap.put("/admin/**", "perms[user:admin]");
		//filterMap.put("/teacher/**", "perms[user:teacher]");
		//添加资源的授权字符串
		//info.addStringPermission("user:add");
		//其中权限一共有以下几种
		//管理员权限 user:admin
		//教师权限   user:teacher
		//教师权限中又包括加班权限/物料权限user:material/user:overwork
		//学生权限 user:student
		//perms参数可以多个，用逗号隔开
//		filterMap.put("/material/decrMaterialNum", "perms[user:material]");
//		filterMap.put("/purchase/addPurchase", "perms[user:material]");
		//filterMap.put("/material/*", "perms[user:material]");
		//filterMap.put("/purchase/*", "perms[user:material]");

		//filterMap.put("/update", "perms[user:update]");
		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/swagger-resources", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/webjars/springfox-swagger-ui/**", "anon");
		//filterMap.put("/**", "authc");

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
	public org.apache.shiro.mgt.SecurityManager getDefaultWebSecurityManager(
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
		return new UserRealm();
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
			                                         , @Qualifier("simpleCookie")SimpleCookie simpleCookie
	){

		MySessionManager mySessionManager= new MySessionManager ();
		mySessionManager.setSessionDAO (sessionDAO);
	//	mySessionManager.setSessionIdCookie (simpleCookie );
		//defaultWebSessionManager.setSessionIdCookieEnabled (false);
	//	mySessionManager.setSessionValidationSchedulerEnabled (false);
	return mySessionManager;
	}
	@Bean("simpleCookie")
	public SimpleCookie getSimpleCookie(){
		SimpleCookie simpleCookie=new SimpleCookie ();
		simpleCookie.setName ("SHIRO-COOKIE");
		simpleCookie.setPath ("/");
		simpleCookie.setDomain (".gxxt.runtofuture.cn");
		simpleCookie.setMaxAge (600000);
		simpleCookie.setHttpOnly (true);
       return simpleCookie;
	}
//	@Bean
//	public FilterRegistrationBean registration() {
//		MyAuthFilter filter=new MyAuthFilter ();
//		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//		registration.setEnabled(false);
//		registration.setOrder (0);
//		return registration;
//	}
	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}


}
