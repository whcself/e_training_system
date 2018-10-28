package com.csu.etrainingsystem.administrator.controller;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * GET     获取一个资源
 * POST    添加一个资源
 * PUT     修改一个资源
 * DELETE  删除一个资源
 */
@RestController
@RequestMapping(value="/admin",method = RequestMethod.POST)
public class AdminController {
    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "创建一个管理员", notes = "")
    @ApiImplicitParam(name = "admin", value = "", required = true, dataType = "Admin")
    @RequestMapping(value="/addAdmin")
    public CommonResponseForm saveAdmin(Admin admin){
        adminService.save(admin);
        return  CommonResponseForm.of204("管理员增加成功");
}

    @RequestMapping(value="/getAdmin/{id}")
    public CommonResponseForm getAdminById(@PathVariable("id") String id){
        Admin admin=adminService.getAdminById(id);
        return  CommonResponseForm.of200("获取管理员成功",admin);
    }

    @RequestMapping(value="/getAllAdmin")
    public CommonResponseForm getAllAdmin(){
        Iterable<Admin> admin=adminService.getAllAdmin();
        return  CommonResponseForm.of200("获取管理员成功",admin);
    }

    @RequestMapping(value="/updateAdmin")
    public CommonResponseForm updateAdmin(Admin admin){
        adminService.updateAdmin(admin);
        return CommonResponseForm.of204("更新管理员成功");
    }

    /*
    修改密码的接口写在admin package里面，还是大package里面
     */
    @RequestMapping(value="/deleteAdmin/{id}")
    public @ResponseBody CommonResponseForm deleteAdmin(@PathVariable("id") String id){
        adminService.deleteAdmin(id);
        return CommonResponseForm.of204("删除管理员成功");
    }


}
