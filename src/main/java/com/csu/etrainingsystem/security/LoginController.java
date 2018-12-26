package com.csu.etrainingsystem.security;

import com.csu.etrainingsystem.form.CommonResponseForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {


	/**
	 * 登录逻辑处理
	 */
	@PostMapping("/login")
	@ResponseBody
	public CommonResponseForm login(String name, String password){
		System.out.println("name="+name);
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取Subject 如果不存在就创建并且绑定到当前线程,如果已经存在就从当前线程拿出来就行了
		Subject subject = SecurityUtils.getSubject();
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);

		//3.执行登录方法
		try {
			subject.login(token);

			//登录成功
			Map<String,String> m=new HashMap<String,String>();
			m.put ("id",name);//name即是id
			return CommonResponseForm.of200 ("登录成功",m);
		} catch (UnknownAccountException e) {
			return CommonResponseForm.of400 ("用户不存在");
		}catch (IncorrectCredentialsException e) {
			return CommonResponseForm.of400 ("用户名或密码不正确");
		}catch (ConcurrentAccessException e){
			return CommonResponseForm.of400 ("重复登录");
		}
	}
	//退出登录
	//tologin
	//noauth
	//
	@RequestMapping("/toLogin")
	@ResponseBody
	public CommonResponseForm toLogin(){
		return  CommonResponseForm.of400 ("用户未登录");
	}
	@RequestMapping("/noAuth")
	@ResponseBody
	public CommonResponseForm onAuth(){
		return  CommonResponseForm.of400 ("您没有访问权限");
	}


}
