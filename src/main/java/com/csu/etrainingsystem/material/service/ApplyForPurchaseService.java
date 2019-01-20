/**
 * 物料先放一放,这里出现了一点问题,字段有需要修改的地方
 */
package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.ApplyForPurchaseRepository;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
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
public class ApplyForPurchaseService {
    private final ApplyForPurchaseRepository applyForPurchaseRepository;
    private final AdminService  adminService;
    private final TeacherService teacherService;
    @Autowired
    public ApplyForPurchaseService(ApplyForPurchaseRepository applyForPurchaseRepository, AdminService adminService, TeacherService teacherService) {
        this.applyForPurchaseRepository = applyForPurchaseRepository;
        this.adminService = adminService;
        this.teacherService = teacherService;
    }

    /**
     * 添加一个申购记录
     * @param applyFpchse
     */
    @Transactional
    public void addApplyFPchse(ApplyForPurchase applyFpchse) {
        this.applyForPurchaseRepository.save(applyFpchse);
    }

    /**
     * 根据物料种类获取申购记录
     * @param clazz 物料种类
     * @return
     */
    @Transactional
    public ApplyForPurchase getPurchaseByClazz(String clazz) {
        Optional<ApplyForPurchase> purchase= applyForPurchaseRepository.findApplyFPchseByClazz (clazz);
        return purchase.get();
    }

    /**
     * 根据起止时间获取申购记录
     * @param startTime
     * @param endTime
     * @return
     */
    @Transactional
    public Iterable<ApplyForPurchase> getPurchaseByTime(String startTime,String endTime) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if(startTime==null||startTime.equals (""))startTime=format.format(applyForPurchaseRepository.findMinTime ());
        if(endTime==null||endTime.equals (""))endTime=format.format(applyForPurchaseRepository.findMaxTime ());

        Iterable<ApplyForPurchase> applyFPchse= applyForPurchaseRepository.findApplyFPchseByTime (startTime,endTime);
        return applyFPchse;
    }

    /**
     * 获取所有的申购记录
     * @return
     */
    @Transactional
    public Iterable<ApplyForPurchase> getAllApplyFPchse() {
        return this.applyForPurchaseRepository.findAllApplyFPchse ();
    }

    /**
     * 更新申购记录,比如审核状态的更新
     * @param
     */
    @Transactional
    public void  updateApplyFPchse(ApplyForPurchase applyForPurchase) {
        this.applyForPurchaseRepository.saveAndFlush(applyForPurchase);
    }

    /**
     * 删除申购记录,真删除还是假删除?这里先考虑真删除
     * @param
     */
    @Transactional
    public void  deleteApplyFPchse(ApplyForPurchase applyForPurchase) {
        this.applyForPurchaseRepository.delete (applyForPurchase);
    }


    /**
     * 获取根据条件选择出来的申购记录
     * @param apply_tname
     * @param clazz
     * @param startTime
     * @param endTime
     * @param pur_tname
     * @param purchase_id
     * @return
     */
    @Transactional
    public Iterable<ApplyForPurchase> getSelectedApplyFPchse( String apply_tname , String clazz,
                                                   String startTime, String endTime,
                                                   String pur_tname,String purchase_id) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println ("输入参数是:"+apply_tname+clazz+startTime+endTime+pur_tname+purchase_id);
        if(startTime==null||startTime.equals (""))startTime=format.format(applyForPurchaseRepository.findMinTime ());
        if(endTime==null||endTime.equals (""))endTime=format.format(applyForPurchaseRepository.findMaxTime ());
        if(clazz==null||clazz.equals (""))clazz="%";
        if(purchase_id==null||purchase_id.equals (""))purchase_id="%";
        if(apply_tname==null||apply_tname.equals ("")||apply_tname.equals ("申购人"))apply_tname="%";
        if(pur_tname==null||pur_tname.equals ("")||pur_tname.equals ("采购人"))pur_tname="%";
        System.out.println ("修正参数是:"+apply_tname+clazz+startTime+endTime+pur_tname+purchase_id);
        return this.applyForPurchaseRepository.getSelectedApplyFPchse (apply_tname ,clazz, startTime,endTime,pur_tname,purchase_id);
    }

    /**
     * 根据权限级别获取每个老师的姓名,如果是管理员,返回其id;
     * @param type
     * @return
     */
    @Transactional
    public List<String> getAllAuthedName(int type) {
        List<String> names=new ArrayList<String> ();
        Iterable<Admin>admins= adminService.getAllAdmin ();
        //所有管理员都拥有各种权限
        for (Admin admin : admins) {names.add (admin.getAid ());}
        Iterable<String>teachers=teacherService.getTeacherByAuth(type);
        for (String teacher : teachers) {
            names.add (teacher);
        }
        return names;
    }
    //根据申购编号获取里面的  申购日期 申购人 物料种类
    //申购数量 申购备注 审核人
    @Transactional
    public List<ApplyForPurchase> getExcelInfos(List<String> purchase_ids) {

        List<ApplyForPurchase> purchases=new ArrayList<> ();
        for (String purchase_id : purchase_ids) {
            purchases.add (this.applyForPurchaseRepository.findApplyFPchseExcelInfos (purchase_id));
        }

        return purchases;
    }
    @Transactional
    public ApplyForPurchase getApplyFPchse(String purchase_id) {
        return this.applyForPurchaseRepository.getOne (purchase_id);
    }
    @Transactional
    public String GeneratePurchaseId() {
        SimpleDateFormat format=new SimpleDateFormat ("yyyyMM");
        String thisMonth= format.format (new Date ());
        System.out.println (thisMonth);
        String maxId=applyForPurchaseRepository.findMaxPurchaseId (thisMonth+"%");
        Integer val=Integer.parseInt (maxId);
        System.out.println (val);
        String id=val+1+"";
        System.out.println (id);
        return id;
    }
}
