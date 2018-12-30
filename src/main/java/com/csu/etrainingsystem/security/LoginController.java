package com.csu.etrainingsystem.security;

import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.entity.UserRole;
import com.csu.etrainingsystem.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
	@Autowired
	private UserService  userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private StudentService studentService;


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
			User user=userService.getUser (name);
			String realName="";
			if (user.getRole ().equals ("teacher")){
				realName=teacherService.getTeacher (name).getTname ();
			}
			else if (user.getRole ().equals ("admin")){
				realName=adminService.getAdminById (name).getAid ();
			}
			else if (user.getRole ().equals ("student")){
				realName=studentService.getStudentById (name).getSname ();

			}
			Map<String,String> m=new HashMap<String,String>();
			m.put ("id",name);//name即是id
			m.put ("身份",user.getRole ());
			m.put ("姓名",realName);
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
	@RequestMapping("/logout")
	@ResponseBody
	public CommonResponseForm loginout(HttpSession session){
		User user=null;
		//1.获取Subject 如果不存在就创建并且绑定到当前线程,如果已经存在就从当前线程拿出来就行了
		try {
			Subject subject = SecurityUtils.getSubject();
			user= UserRole.getUser (session);
			subject.logout ();
		}catch (Exception e){
			e.printStackTrace ();
		}finally {
			return  CommonResponseForm.of204 ("退出登录成功");
		}


	}
	//tologin

	@RequestMapping("/toLogin")
	@ResponseBody
	public CommonResponseForm toLogin(){
		return  CommonResponseForm.of400 ("用户未登录");
	}
	//noauth
	@RequestMapping("/noAuth")
	@ResponseBody
	public CommonResponseForm onAuth(){
		return  CommonResponseForm.of400 ("您没有访问权限");
	}

//	@RequestMapping("/abc")
//    public String abc(){
//	    return "login.html";
//    }
//    @PostMapping("/tlogin")
//    public String tologin(String name, String password){
//        System.out.println("name="+name);
//        /**
//         * 使用Shiro编写认证操作
//         */
//        //1.获取Subject 如果不存在就创建并且绑定到当前线程,如果已经存在就从当前线程拿出来就行了
//        Subject subject = SecurityUtils.getSubject();
//        //2.封装用户数据
//        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
//
//        //3.执行登录方法
//            subject.login(token);
//
//       return "test.html";
//    }

}
