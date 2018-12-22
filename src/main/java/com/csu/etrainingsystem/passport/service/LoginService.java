package com.csu.etrainingsystem.passport.service;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	CommonResponseForm login(String account,String password, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	CommonResponseForm getUserInfoByToken(String token);

	/**
	 * 退出
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	CommonResponseForm logout(String token, HttpServletRequest request, HttpServletResponse response);
}
