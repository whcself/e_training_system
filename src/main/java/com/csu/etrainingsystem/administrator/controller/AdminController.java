package com.csu.etrainingsystem.administrator.controller;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.MarkingService;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.service.UserService;
import com.csu.etrainingsystem.util.ExceptionPrint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;


/**
 * GET     获取一个资源
 * POST    添加一个资源
 * PUT     修改一个资源
 * DELETE  删除一个资源
 * <p>
 * 方法习惯：管理员的权限，功能都写在了controller中，比如导入学生，marking的CRUD
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final MarkingService markingService;
    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, TeacherService teacherService, MarkingService markingService, UserService userService) {
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.markingService = markingService;
        this.userService = userService;
    }


    public CommonResponseForm check(){

        return CommonResponseForm.of204("ss");
    }

    @PostMapping(value = "/addAdmin")
    public CommonResponseForm addAdmin(Admin admin,String adminName) {
        adminService.save(admin);
        Teacher teacher=new Teacher ();
        teacher.setRole ("管理员");
        teacher.setTname (adminName);
        teacher.setTid (admin.getAid ());
        teacher.setMaterial_privilege(63);
        teacher.setOvertime_privilege(1);
        teacherService.addTeacher(teacher,null);
        return CommonResponseForm.of204("管理员增加成功");
    }


    @Pointcut
    @PostMapping(value = "/getAdmin/{id}")
    public CommonResponseForm getAdminById(@PathVariable("id") String id) {
        Admin admin = adminService.getAdminById(id);
        return CommonResponseForm.of200("获取管理员成功", admin);
    }

    @Pointcut
    @PostMapping(value = "/getAllAdmin")
    public CommonResponseForm getAllAdmin() {
        Iterable<Admin> admin = adminService.getAllAdmin();
        return CommonResponseForm.of200("获取管理员成功", admin);
    }

    @Pointcut
    @PostMapping(value = "/updateAdmin")
    public CommonResponseForm updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        return CommonResponseForm.of204("更新管理员成功");
    }
    @Pointcut
    @PostMapping(value = "/deleteAdmin/{id}")
    public @ResponseBody
    CommonResponseForm deleteAdmin(@PathVariable("id") String id) {
        adminService.deleteAdmin(id);
        userService.deleteUser (id);
        return CommonResponseForm.of204("删除管理员成功");
    }


    /*
    marking CRUD
     */
    @Pointcut
    @PostMapping("/addMarking")
    public CommonResponseForm addMarking(Marking marking) {
        markingService.addMarking(marking);
        return CommonResponseForm.of204("增加管理权限条目成功");
    }
    @Pointcut
    @PostMapping("/deleteMarking")
    public CommonResponseForm deleteMarking(@RequestParam String markName) {
        markingService.deleteMarking(markName);
        return CommonResponseForm.of204("删除管理权限条目成功");
    }
    @Pointcut
    @PostMapping("/updateMarking")
    public CommonResponseForm updateMarking(Marking marking) {
        markingService.updateMarking(marking);
        return CommonResponseForm.of204("修改管理权限条目成功");
    }

    //查询单个管理权限条目没写，没用
    @PostMapping("/getAllMarking")
    public CommonResponseForm getAllMarking() {
        return CommonResponseForm.of200("获取管理权限条目列表成功", markingService.getAllMarking());
    }

    /*
    修改密码的接口写在admin package里面，还是大package里面
     */


    /**
     * @apiNote 导入学生
     * @param request 没用
     * @param file file
     * @param batchName 批次
     * @return
     */
    @PostMapping("/importStudents")
    public CommonResponseForm importStudents(HttpServletRequest request,
                                             MultipartFile file,
                                             @RequestParam String batchName) {
        try{
            return adminService.importStudent(file,batchName);

        } catch (Exception e) {
            return CommonResponseForm.of400(ExceptionPrint.get(e));

        }
    }




    /**
     * @apiNote 教师管理-查询教师
     * @param tClass 教师组
     * @param role 角色
     * @return list
     */
    @PostMapping("/findTeachers")
    public CommonResponseForm findTeachers(@RequestParam(required = false) String tClass,
                                           @RequestParam(required = false) String role,
                                           @RequestParam(required = false) String material_privilege,
                                           @RequestParam(required = false) String overwork_privilege) {

        try {
            List<Map<String,String>> teachers = teacherService.findTeachers(tClass, role, material_privilege, overwork_privilege);
            return CommonResponseForm.of200("查询成功", teachers);
        } catch (Exception e) {
            return CommonResponseForm.of400("查询错误");
        }
    }

    /**
     * @apiNote 管理员端-下载导入模板
     * @param request r
     * @param response r
     */
    @GetMapping("/download")
    public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        adminService.downloadTemplate(request,response,1);
    }

    /**
     * @apiNote 下载成绩模板
     */
    @GetMapping("/downloadScore")
    public void downloadScoreTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        adminService.downloadTemplate(request,response,2);
    }


}
