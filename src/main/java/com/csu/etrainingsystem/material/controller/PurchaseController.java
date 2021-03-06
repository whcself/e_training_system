package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.form.UpdateForm;
import com.csu.etrainingsystem.material.service.PurchaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * -ScJn
     * @apiNote 采购列表
     * @param purchase_id 申购编号
     * @param clazz 物料种类
     * @param pur_tname 采购人
     * @param begin 开始时间
     * @param end 结束时间
     *
     */
    @PostMapping("/getPurchase")
    public CommonResponseForm getPurchase(@RequestParam String purchase_id,
                                          @RequestParam String clazz,
                                          @RequestParam String pur_tname,
                                          @RequestParam String begin,
                                          @RequestParam String end){

        List<Purchase> purchases=purchaseService.getPurchase(purchase_id,clazz, pur_tname, begin, end);
        return CommonResponseForm.of200("共"+purchases.size()+"条记录",purchases);
    }

    /**
     * -ScJn
     * @apiNote 下载采购单
     * @param purchaseIds 采购编号列表
     */
    @PostMapping("/downloadPurchase")
    public void downloadPurchase(HttpServletResponse response,
                                  @RequestBody  String[] purchaseIds) throws IOException {
        purchaseService.downloadPurchase(response,purchaseIds);
    }

    /**
     * -ScJn
     * @apiNote 新增采购记录，判断对应申购是否被审批，采购数量是否超出。
     * @param purchase purchase 实体
     */
    @PostMapping("/add")
//    @RequiresPermissions(value = "material:MATERIAL_PURCHASE")
    public CommonResponseForm addPurchase(Purchase purchase,
                                          @RequestParam String tName){
        return purchaseService.addPurchase(purchase,tName);
    }

    @PostMapping("/clazz")
    public CommonResponseForm getClazzByTName(@RequestParam String tName){
        System.out.println(tName);
        return CommonResponseForm.of200("查询成功",purchaseService.getClazzByTName(tName));
    }

    /**
     * -ScJn
     * @apiNote 删除采购记录
     * @param ids 采购ids
     */
    @PostMapping("/delete")
    public CommonResponseForm delete(@RequestBody String[] ids){
        purchaseService.delete2(ids);
        return CommonResponseForm.of204("删除成功");
    }


    /**
     * -ScJn
     * @apiNote 修改采购数目
     */
    @PostMapping("/updateNum")
    public CommonResponseForm updateNum(@RequestBody UpdateForm updateForm){
        return purchaseService.updateNum(updateForm);
    }
}
