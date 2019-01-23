package com.csu.etrainingsystem.material.form;

import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.service.PurchaseService;
import com.csu.etrainingsystem.material.service.ReimbursementService;
import com.csu.etrainingsystem.material.service.SaveService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplyFPchseForm {


    private String purchase_id;//申购编号
    private String clazz ;//名称+型号
    private String apply_tname ;//申购老师
    private String apply_vert_tname;//申购审核老师
    
   // private String apply_vert_time;//申购审核时间
    private String apply_time ;//申购时间
    private String apply_remark ;//申购备注
    private Integer    apply_num;//申购数量
    private Boolean apply_vertify;//申购审核状态
    private String pur_tname;//采购老师
    private Integer pur_num;//采购数量
    private Integer  save_num;//入库数量
    private Integer    remib_num;//报账总数

    public ApplyFPchseForm(ApplyForPurchase applyForPurchase) {
        this.purchase_id=applyForPurchase.getPurchase_id ();
        this.clazz=applyForPurchase.getClazz ();
        this.apply_tname=applyForPurchase.getApply_tname ();
        this.apply_vert_tname=applyForPurchase.getApply_vert_tname ();
        this.apply_time=applyForPurchase.getApply_time ();
        this.apply_remark=applyForPurchase.getApply_remark ();
        this.apply_num=applyForPurchase.getApply_num ();
        this.apply_vertify=applyForPurchase.getApply_verify ();
        this.pur_tname=applyForPurchase.getPur_tname ();
    }

    public static List<ApplyFPchseForm> wrapForm(Iterable<ApplyForPurchase> purchases,
                                                 PurchaseService purchaseService,
                                                 ReimbursementService reimbursementService,
                                                 SaveService saveService){
        List<ApplyFPchseForm> form=new ArrayList<> ();
       for (ApplyForPurchase purchase : purchases) {
           if (purchase==null)continue;
            ApplyFPchseForm afpf=new ApplyFPchseForm (purchase);
            Integer puNum=purchaseService.getAllPerNumByPId (purchase.getPurchase_id ());
            if (puNum!=null)afpf.setPur_num (puNum);
            else afpf.setPur_num(0);
           Integer rimbNum=reimbursementService.getAllReimbNumByPId (purchase.getPurchase_id ());
           if (rimbNum!=null)afpf.setRemib_num (rimbNum);
           else afpf.setRemib_num(0);
           Integer saveNum=saveService.getAllSaveNum (purchase.getPurchase_id ());
           if (saveNum!=null)afpf.setSave_num (saveNum);
           else afpf.setSave_num(0);
            form.add (afpf);
        }
        return form;
    }
}
