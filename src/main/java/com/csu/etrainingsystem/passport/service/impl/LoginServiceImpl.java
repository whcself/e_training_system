package com.csu.etrainingsystem.passport.service.impl;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.passport.service.LoginService;
import com.csu.etrainingsystem.redis.dao.JedisDao;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import com.csu.etrainingsystem.util.CookieUtils;
import com.csu.etrainingsystem.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private UserService userService;
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public CommonResponseForm login(User user, HttpServletRequest request, HttpServletResponse response) {

		User dbuser = userService.getUser (user.getAccount ());
		if(dbuser!=null){
			//当用户登录成功后把用户信息放入到redis中
			String key = UUID.randomUUID().toString();
			//将用户放入redis中
			jedisDaoImpl.set(key, JsonUtils.objectToJson(dbuser));
			//设置过期时间
			jedisDaoImpl.expire(key, 60*60*24*7);
			//产生Cookie
			CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60*60*24*7);
		}else{
			return CommonResponseForm.of400 ("用户名或密码错误");
		}
		return CommonResponseForm.of204 ("登录成功");
	}

	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	@Override
	public CommonResponseForm getUserInfoByToken(String token) {
		//redis存放的是json
		String json = jedisDaoImpl.get(token);
		User user;
		if(json!=null&&!json.equals("")){
			 user = JsonUtils.jsonToPojo(json, User.class);
			//可以把密码清空
			user.setPwd (null);
		}else{
			return CommonResponseForm.of400 ("获取信息失败");
		}
		
		return  CommonResponseForm.of200 ("获取信息成功",user);
	}
	@Override
	public CommonResponseForm logout(String token, HttpServletRequest request, HttpServletResponse response) {
		Long index = jedisDaoImpl.del(token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		return CommonResponseForm.of204 ("退出登录成功");
	}

}
