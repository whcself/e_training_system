package com.csu.etrainingsystem.security;

import com.csu.etrainingsystem.form.CommonResponseForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


	/**
	 * 登录逻辑处理
	 */
	@PostMapping("/login")
	public CommonResponseForm login(String name, String password){
		System.out.println("name="+name);
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();

		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);

		//3.执行登录方法
		try {
			subject.login(token);



			//登录成功
			//跳转到test.html
			return CommonResponseForm.of204 ("登录成功");
		} catch (UnknownAccountException e) {
			//e.printStackTrace();
			//登录失败:用户名不存在

			return CommonResponseForm.of400 ("用户名或密码不正确");
		}catch (IncorrectCredentialsException e) {

			return CommonResponseForm.of400 ("用户名或密码不正确");
		}catch (ConcurrentAccessException e){

			return CommonResponseForm.of400 ("重复登录");
		}
	}
}
