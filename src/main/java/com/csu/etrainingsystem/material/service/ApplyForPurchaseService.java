/**
 * 物料先放一放,这里出现了一点问题,字段有需要修改的地方
 */
package com.csu.etrainingsystem.material.service;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.ApplyForPurchaseRepository;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;


@Service
public class ApplyForPurchaseService {
    private final ApplyForPurchaseRepository applyForPurchaseRepository;
    private final AdminService adminService;
    private final TeacherService teacherService;

    @Autowired
    public ApplyForPurchaseService(ApplyForPurchaseRepository applyForPurchaseRepository, AdminService adminService, TeacherService teacherService) {
        this.applyForPurchaseRepository = applyForPurchaseRepository;
        this.adminService = adminService;
        this.teacherService = teacherService;
    }

    /**
     * 添加一个申购记录
     *
     * @param applyFpchse
     */
    @Transactional
    public void addApplyFPchse(ApplyForPurchase applyFpchse) {
        this.applyForPurchaseRepository.saveAndFlush (applyFpchse);
    }

    /**
     * 根据物料种类获取申购记录
     *
     * @param clazz 物料种类
     * @return
     */
    @Transactional
    public ApplyForPurchase getPurchaseByClazz(String clazz) {
        Optional<ApplyForPurchase> purchase = applyForPurchaseRepository.findApplyFPchseByClazz (clazz);
        return purchase.get ();
    }

    /**
     * 根据起止时间获取申购记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Transactional
    public Iterable<ApplyForPurchase> getPurchaseByTime(String startTime, String endTime) {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
        if (startTime == null || startTime.equals (""))
            startTime = format.format (applyForPurchaseRepository.findMinTime ());
        if (endTime == null || endTime.equals ("")) endTime = format.format (applyForPurchaseRepository.findMaxTime ());

        Iterable<ApplyForPurchase> applyFPchse = applyForPurchaseRepository.findApplyFPchseByTime (startTime, endTime);
        return applyFPchse;
    }

    /**
     * 获取所有的申购记录
     *
     * @return
     */
    @Transactional
    public Iterable<ApplyForPurchase> getAllApplyFPchse() {
        return this.applyForPurchaseRepository.findAllApplyFPchse ();
    }

    /**
     * 更新申购记录,比如审核状态的更新
     *
     * @param
     */
    @Transactional
    public void updateApplyFPchse(ApplyForPurchase applyForPurchase) {
        this.applyForPurchaseRepository.saveAndFlush (applyForPurchase);
    }

    /**
     * 删除申购记录,真删除还是假删除?这里先考虑真删除
     *
     * @param
     */
    @Transactional
    public void deleteApplyFPchse(ApplyForPurchase applyForPurchase) {
        this.applyForPurchaseRepository.delete (applyForPurchase);
    }

    @Transactional
    public void deleteApplyFPchseById(String purchase_id) {
        this.applyForPurchaseRepository.deleteById (purchase_id);
    }


    /**
     * 获取根据条件选择出来的申购记录
     *方法过时
     * @param apply_tname
     * @param clazz
     * @param startTime
     * @param endTime
     * @param pur_tname
     * @param purchase_id
     * @return
     */
//    @Transactional
//    public Iterable<ApplyForPurchase> getSelectedApplyFPchse(Boolean apply_verify ,String apply_tname , String clazz,
//                                                   String startTime, String endTime,
//                                                   String pur_tname,String purchase_id) {
//        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
//        System.out.println ("输入参数是:" + apply_tname + clazz + startTime + endTime + pur_tname + purchase_id);
//        if (startTime == null || startTime.equals (""))
//            startTime = format.format (applyForPurchaseRepository.findMinTime ());
//        String status="";
//        if(apply_verify==null)status="%";
//        else if (apply_verify)status="1";
//        else status="0";
//        if (endTime == null || endTime.equals ("")) endTime = format.format (applyForPurchaseRepository.findMaxTime ());
//        if (clazz == null || clazz.equals ("")) clazz = "%";
//        if (purchase_id == null || purchase_id.equals ("")) purchase_id = "%";
//        if (apply_tname == null || apply_tname.equals ("") || apply_tname.equals ("申购人")) apply_tname = "%";
//        if (pur_tname == null || pur_tname.equals ("") || pur_tname.equals ("采购人")) pur_tname = "%";
//        System.out.println ("修正参数是:"+status + apply_tname + clazz + startTime + endTime + pur_tname + purchase_id);
//         return this.applyForPurchaseRepository.getSelectedApplyFPchse (status,apply_tname, clazz, pur_tname, purchase_id, startTime, endTime);
//    }
    @Transactional
    public Iterable<ApplyForPurchase> getSelectedApplyFPchse(Boolean apply_verify, String apply_tname, String clazz,
                                                             String startTime, String endTime,
                                                             String pur_tname, String purchase_id) {
        /**
         * Specification<Users>:用于封装查询条件
         */
        Specification<ApplyForPurchase> spec = new Specification<ApplyForPurchase> () {

            //Predicate:封装了 单个的查询条件
            /**
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<ApplyForPurchase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<> ();
                list.add (cb.equal (root.get ("del_status").as (Boolean.class),false));
                //如果输入了id就不必继续判断,直接返回
                if (!StringUtils.isEmpty (purchase_id)){
                    list.add (cb.equal (root.get ("purchase_id").as (String.class), purchase_id));
                    Predicate[] arr = new Predicate[list.size ()];
                    return cb.and (list.toArray (arr));
                }
                if (apply_verify==null){

                }else if (apply_verify){
                    list.add (cb.equal (root.get ("apply_verify").as (Boolean.class), true));
                }
                else if (!apply_verify){
                    list.add (cb.equal (root.get ("apply_verify").as (Boolean.class), false));
                }

                if (!StringUtils.isEmpty (startTime)){
                 list.add (cb.greaterThanOrEqualTo (root.get ("applyTime").as (String.class),startTime));
                }
                if (!StringUtils.isEmpty (endTime)){
                    list.add (cb.lessThanOrEqualTo (root.get ("applyTime").as (String.class),endTime));
                }

                if (!StringUtils.isEmpty (clazz)){
                    list.add (cb.equal (root.get ("clazz").as (String.class), clazz));
                }
                if (!StringUtils.isEmpty (apply_tname)){
                    if (!apply_tname.equals ("申购人")) {
                        list.add (cb.equal (root.get ("apply_tname").as (String.class), apply_tname));
                    }
                }
                if (!StringUtils.isEmpty (pur_tname)){
                    if (!pur_tname.equals ("采购人")) {
                        list.add (cb.equal (root.get ("pur_tname").as (String.class), pur_tname));
                    }
                }

                Predicate[] arr = new Predicate[list.size ()];
                return cb.and (list.toArray (arr));
            }
        };
        Sort sort = new Sort(new Sort.Order (Sort.Direction.DESC,"applyTime"));
        List<ApplyForPurchase> list = this.applyForPurchaseRepository.findAll (spec,sort);
        return list;
    }

    /**
     * 根据权限级别获取每个老师的姓名,如果是管理员,返回其id;
     *
     * @param type
     * @return
     */
    @Transactional
    public List<String> getAllAuthedName(int type) {
        List<String> names = new ArrayList<String> ();

        Iterable<String> teachers = teacherService.getTeacherByAuth (type);
        for (String teacher : teachers) {
            names.add (teacher);
        }
        return names;
    }

    //根据申购编号获取里面的  申购日期 申购人 物料种类
    //申购数量 申购备注 审核人
    @Transactional
    public List<ApplyForPurchase> getExcelInfos(List<String> purchase_ids) {

        List<ApplyForPurchase> purchases = new ArrayList<> ();
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
        SimpleDateFormat format = new SimpleDateFormat ("yyyyMM");
        String thisMonth = format.format (new Date ());
        System.out.println (thisMonth);
        String maxId = applyForPurchaseRepository.findMaxPurchaseId (thisMonth + "%");
        //如果本月还没有任何申请,这就需要手动创建第一个
        Integer val = null;
        String id = "";
        if (maxId != null) {
            val = Integer.parseInt (maxId);
            id = val + 1 + "";
        } else {
            id = thisMonth + "01";
        }
        return id;
    }
}
