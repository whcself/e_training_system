package com.csu.etrainingsystem.passport.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.passport.service.LoginService;
import com.csu.etrainingsystem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TbUserController {
	@Autowired
	private LoginService loginServiceImpl;
	/**
	 * 显示登录页面
	 * @return
	 */
	@PostMapping("/showLogin")
	public String showLogin(@RequestHeader("Referer") String url, Model model){
		model.addAttribute("redirect", url);
		return "login";
	}
	/**
	 * 登录
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public CommonResponseForm login(String name,String password, HttpServletRequest request, HttpServletResponse response){
		System.out.println ("name="+name);
		return loginServiceImpl.login(name,password,request,response);
	}
	/**
	 * 通过token获取用户信息,将token放入请求头中
	 * @param token
	 * @return
	 */
	@PostMapping("user/token/{token}")
	@ResponseBody
	public Object getUserInfo(@PathVariable String token
			//, String callback
	){
//		EgoResult er = ;
//		if(callback!=null&&!callback.equals("")){
//			MappingJacksonValue mjv = new MappingJacksonValue (er);
//			mjv.setJsonpFunction(callback);
//			return mjv;
//		}
		return loginServiceImpl.getUserInfoByToken(token);
	}
	/**
	 * 退出
	 * @param token
	 * @return
	 */
	@PostMapping("user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token,
						 //String callback,
						 HttpServletRequest request, HttpServletResponse response){

//		if(callback!=null&&!callback.equals("")){
//			MappingJacksonValue mjv = new MappingJacksonValue (er);
//			mjv.setJsonpFunction(callback);
//			return mjv;
//		}
		return loginServiceImpl.logout(token,request,response);
	}
}
