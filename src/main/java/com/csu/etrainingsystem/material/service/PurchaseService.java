package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.ApplyForPurchaseRepository;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ApplyForPurchaseRepository applyForPurchaseRepository;
    @Autowired
    public PurchaseService(ApplyForPurchaseRepository applyForPurchaseRepository,PurchaseRepository purchaseRepository) {
        this.applyForPurchaseRepository=applyForPurchaseRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getPurchase(String purchase_id,
                                      String clazz,
                                      String pur_name,
                                      String begin,
                                      String end) {

        return purchaseRepository.findPurchasesBy4Desc(purchase_id, clazz, pur_name, begin, end);

    }

    public CommonResponseForm addPurchase(Purchase purchase) {
        String pid = purchase.getPurchase_id();
        ApplyForPurchase infoMap = applyForPurchaseRepository.
                getPurchaseInfo(pid);
        purchase.setDel_status(false);
        purchase.setPur_tname(infoMap.getPur_tname());  // set the teacher name
        purchase.setClazz(infoMap.getClazz());
        int num = purchaseRepository.getAllPurNum(pid);  //judge the purchase total number
        if (!infoMap.getApply_verify()) {
            return CommonResponseForm.of400("该申购未被审核");
        } else if (purchase.getPur_num() + num > infoMap.getApply_num()) {
            return CommonResponseForm.of400("采购总量超出申购数量");
        }

        purchaseRepository.saveAndFlush(purchase);
        return CommonResponseForm.of204("增加成功");
    }
    public Integer getAllPerNumByPId(String pid){
      return   purchaseRepository.getAllPurNum (pid);
    }
    public void downloadPurchase(HttpServletResponse response,
                                 String[] purchaseIds) throws IOException {
        List<Purchase> purchases = new ArrayList<>();
        for (String key : purchaseIds) {
            purchases.add(purchaseRepository.findByPId(key));
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("采购表");
        String[] headers = {
                "申购编号",
                "采购日期",
                "采购人",
                "物料种类",
                "采购数量",
                "采购备注"
        };
        String fileName = "purchase" + System.currentTimeMillis() + ".xls";//设置要导出的文件的名字

        //新增数据行，并且设置单元格数据
        HSSFRow row = sheet.createRow(0);
        int rowNum = purchaseIds.length;

        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (int i = 1; i <= rowNum; i++) {
            HSSFRow r = sheet.createRow(i);
            r.createCell(0).setCellValue(purchases.get(i - 1).getPurchase_id());
            r.createCell(1).setCellValue(purchases.get(i - 1).getPur_time());
            r.createCell(2).setCellValue(purchases.get(i - 1).getPur_tname());
            r.createCell(3).setCellValue(purchases.get(i - 1).getClazz());
            r.createCell(4).setCellValue(purchases.get(i - 1).getPur_num());
            r.createCell(5).setCellValue(purchases.get(i - 1).getPur_remark());
        }
//);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public List<String> getClazzByTName(String tName){
        return purchaseRepository.getClazzByTName(tName);
    }
}
