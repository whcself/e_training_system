package com.csu.etrainingsystem.security;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.SpStudentRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.entity.UserRole;
import com.csu.etrainingsystem.user.repository.UserRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping(method = RequestMethod.POST)
public class LoginController2 {


    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SpStudentRepository spStudentRepository;

    @Autowired
    public LoginController2(UserRepository userRepository, SpStudentRepository spStudentRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.spStudentRepository=spStudentRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 登录逻辑处理
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonResponseForm login(String name, String password, HttpSession session) {

        String id = name;

        User user = userRepository.findUserByAccount(id);
        if(user==null)return CommonResponseForm.of400("账号不存在");
        if (!password.equals(user.getPwd())) return CommonResponseForm.of400("密码错误");

        if(session.getAttribute("id")!=null){
            session.invalidate();
            return CommonResponseForm.of400("重复登录");
        }
        switch (user.getRole()) {
            case "teacher":
            case "admin":
                Teacher teacher = teacherRepository.findTeacherByTid(id);
                session.setAttribute("name", teacher.getTname());
                session.setAttribute("role", teacher.getRole());
                session.setAttribute("id", teacher.getTid());
                session.setAttribute("material", teacher.getMaterial_privilege());
                session.setAttribute("overwork", teacher.getOvertime_privilege());
                break;
            case "student":
                Student student = studentRepository.findStudentBySid(id).get();
                session.setAttribute("id", student.getSid());
                session.setAttribute("batchName", student.getBatch_name());
                session.setAttribute("name", student.getSname());
                session.setAttribute("sGroupId", student.getS_group_id());
                session.setAttribute("clazz", student.getClazz());
                session.setAttribute("role", "student");
                break;
            /**
             * 特殊学生页需要登录
             */
            case "spStudent":
                SpecialStudent specialStudent = spStudentRepository.findSpStudentBySid(id).get();
                session.setAttribute("id", specialStudent.getSid());
                session.setAttribute("templateName", specialStudent.getTemplate_name());
                session.setAttribute("name", specialStudent.getSname());
                session.setAttribute("clazz", specialStudent.getClazz());
                session.setAttribute("role", "spStudent");
                break;
        }

        return CommonResponseForm.of204("登录成功");

    }

    //退出登录
    @PostMapping("/logout")
    @ResponseBody
    public CommonResponseForm loginout(HttpSession session) {
        User user = null;
        //1.获取Subject 如果不存在就创建并且绑定到当前线程,如果已经存在就从当前线程拿出来就行了
        try {
            Subject subject = SecurityUtils.getSubject();
            user = UserRole.getUser(session);
            subject.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return CommonResponseForm.of204("退出登录成功");
        }


    }
    //tologin

    @PostMapping("/toLogin")
    public String toLogin() {
        return "redirect:/csu-engineer-train-front/login.html";
    }

    //noauth
    @PostMapping("/noAuth")
    @ResponseBody
    public CommonResponseForm onAuth() {
        return CommonResponseForm.of400("您没有访问权限");
    }

    @PostMapping("/abc")
    public String abc() {
        return "login.html";
    }

    @PostMapping("/tlogin")
    public String tologin(String name, String password) {
        System.out.println("name=" + name);
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject 如果不存在就创建并且绑定到当前线程,如果已经存在就从当前线程拿出来就行了
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        //3.执行登录方法
        subject.login(token);

        return "test.html";
    }

}
