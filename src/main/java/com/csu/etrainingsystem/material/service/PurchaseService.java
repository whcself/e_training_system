/**
 * 物料先放一放,这里出现了一点问题,字段有需要修改的地方
 */
package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final AdminService  adminService;
    private final TeacherService teacherService;
    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, AdminService adminService, TeacherService teacherService) {
        this.purchaseRepository = purchaseRepository;
        this.adminService = adminService;
        this.teacherService = teacherService;
    }

    /**
     * 添加一个申购记录,前端页面空缺
     * @param purchase
     */
    @Transactional
    public void addPurchase(Purchase purchase) {
        this.purchaseRepository.save(purchase);
    }
    @Transactional
    public Purchase getPurchase(String clazz) {
        Optional<Purchase> purchase=purchaseRepository.findPurchaseByClazz(clazz);
        return purchase.get();
    }
    @Transactional
    public Iterable<Purchase> getPurchaseByTime(String startTime,String endTime) {
        Iterable<Purchase> purchases=purchaseRepository.findPurchaseByTime (startTime,endTime);
        return purchases;
    }
    @Transactional
    public Iterable<Purchase> getAllPurchase() {
        return this.purchaseRepository.findAllPurchase();
    }

    @Transactional
    public void  updatePurchase(Purchase Purchase) {
        this.purchaseRepository.saveAndFlush(Purchase);
    }



    @Transactional
    public Iterable<Purchase> getSelectedPurchase( String tname , String clazz, String startTime, String endTime) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        if(startTime==null||startTime.equals (""))startTime=format.format(purchaseRepository.findMinTime ());
        System.out.println ("开始时间"+startTime);
        if(endTime==null||endTime.equals (""))endTime=format.format(purchaseRepository.findMaxTime ());
        System.out.println ("截止时间"+endTime);
        if(tname==null||tname.equals ("")||tname.equals ("申购人"))tname="%";if(clazz==null||clazz.equals (""))clazz="%";
        return this.purchaseRepository.getSelectedPurchase(tname ,clazz, startTime,endTime);
    }
    @Transactional
    public List<String> getAllPurchaserName() {
        List<String> names=new ArrayList<String> ();
        Iterable<Admin>admins= adminService.getAllAdmin ();
        //所有管理员都拥有这种权限
        for (Admin admin : admins) {
            names.add (admin.getAid ());
        }
        Iterable<Teacher>teachers=teacherService.getAllTeacher ();
        for (Teacher teacher : teachers) {
            //部分老师用有这种权限
            if(teacher.getMaterial_privilege ()==1)  names.add (teacher.getTname ());

        }
        return names;
    }

}
