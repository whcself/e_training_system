package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getPurchase(String purchase_id,
                                      String clazz,
                                      String pur_name,
                                      String begin,
                                      String end) {

        return  purchaseRepository.findPurchasesBy4Desc(purchase_id, clazz, pur_name, begin, end);

    }

    public void addPurchase(Purchase purchase){
        int num=purchase.getPur_num();

        purchaseRepository.saveAndFlush(purchase);
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
            r.createCell(5).setCellValue(purchases.get(i - 1).getPur_remFark());
        }
//);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" +fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
