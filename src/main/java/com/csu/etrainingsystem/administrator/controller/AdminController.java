package com.csu.etrainingsystem.administrator.controller;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.authority.TeacherAuthority;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.MarkingService;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * GET     获取一个资源
 * POST    添加一个资源
 * PUT     修改一个资源
 * DELETE  删除一个资源
 * <p>
 * 方法习惯：管理员的权限，功能都写在了controller中，比如导入学生，marking的CRUD
 */
@RestController
@RequestMapping(value = "/admin", method = RequestMethod.POST)
public class AdminController {
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final MarkingService markingService;

    @Autowired
    public AdminController(AdminService adminService, TeacherService teacherService,MarkingService markingService) {
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.markingService = markingService;
    }

    @RequestMapping(value = "/addAdmin")
    public CommonResponseForm addAdmin(Admin admin) {
        adminService.save(admin);
        return CommonResponseForm.of204("管理员增加成功");
    }

    @RequestMapping(value = "/getAdmin/{id}")
    public CommonResponseForm getAdminById(@PathVariable("id") String id) {
        Admin admin = adminService.getAdminById(id);
        return CommonResponseForm.of200("获取管理员成功", admin);
    }

    @RequestMapping(value = "/getAllAdmin")
    public CommonResponseForm getAllAdmin() {
        Iterable<Admin> admin = adminService.getAllAdmin();
        return CommonResponseForm.of200("获取管理员成功", admin);
    }

    @RequestMapping(value = "/updateAdmin")
    public CommonResponseForm updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        return CommonResponseForm.of204("更新管理员成功");
    }

    @RequestMapping(value = "/deleteAdmin/{id}")
    public @ResponseBody
    CommonResponseForm deleteAdmin(@PathVariable("id") String id) {
        adminService.deleteAdmin(id);
        return CommonResponseForm.of204("删除管理员成功");
    }


    /*
    marking CRUD
     */

    @RequestMapping("/addMarking")
    public CommonResponseForm addMarking(Marking marking) {
        markingService.addMarking(marking);
        return CommonResponseForm.of204("增加管理权限条目成功");
    }

    @RequestMapping("/deleteMarking")
    public CommonResponseForm deleteMarking(@RequestParam String markName) {
        markingService.deleteMarking(markName);
        return CommonResponseForm.of204("删除管理权限条目成功");
    }

    @RequestMapping("/updateMarking")
    public CommonResponseForm updateMarking(Marking marking) {
        markingService.updateMarking(marking);
        return CommonResponseForm.of204("修改管理权限条目成功");
    }

    //查询单个管理权限条目没写，没用
    @RequestMapping("/getAllMarking")
    public CommonResponseForm getAllMarking() {
        return CommonResponseForm.of200("获取管理权限条目列表成功", markingService.getAllMarking());
    }

    /*
    修改密码的接口写在admin package里面，还是大package里面
     */


    /*
    导入学生接口
     */
    @PostMapping("/importStudents")
    public CommonResponseForm importStudents(@RequestParam String path,
                                             @RequestParam String batchName) {
        ArrayList<Student> students = adminService.importStudent(path,batchName);
        return CommonResponseForm.of200("导入学生信息成功", students);
    }


    @RequestMapping("/findTeachers")
    public CommonResponseForm findTeachers(@RequestParam(required = false) String tClass,
                                           @RequestParam(required = false) String role,
                                           @RequestParam(required = false) String material_privilege,
                                           @RequestParam(required = false) String overwork_privilege) {
        try {
            List<Teacher> teachers = teacherService.findTeachers(tClass, role, material_privilege, overwork_privilege);
            return CommonResponseForm.of200("查询成功", teachers);
        } catch (Exception e) {
            return CommonResponseForm.of400("查询错误");
        }
    }

    /**
     * @apiNote 管理员端-下载成绩模板
     * @param request r
     * @param response r
     */
    @PostMapping("/download")
    public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        adminService.downloadTemplate(request,response);
    }


}
