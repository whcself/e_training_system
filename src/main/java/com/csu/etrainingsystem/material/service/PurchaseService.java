package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.form.UpdateForm;
import com.csu.etrainingsystem.material.repository.ApplyForPurchaseRepository;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.user.entity.UserRole;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ApplyForPurchaseRepository applyForPurchaseRepository;

    @Autowired
    public PurchaseService(ApplyForPurchaseRepository applyForPurchaseRepository, PurchaseRepository purchaseRepository) {
        this.applyForPurchaseRepository = applyForPurchaseRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getPurchase(String purchase_id,
                                      String clazz,
                                      String pur_name,
                                      String begin,
                                      String end) {

        return purchaseRepository.findPurchasesBy4Desc(purchase_id, clazz, pur_name, begin, end);

    }


    public CommonResponseForm addPurchase(Purchase purchase, String tName) {
        String pid = purchase.getPurchase_id();
        ApplyForPurchase infoMap = applyForPurchaseRepository.
                getPurchaseInfo(pid);
        if (infoMap == null || !infoMap.getPur_tname().equals(tName)) {
            return CommonResponseForm.of400("申购不存在或者采购老师不符合");
        }
        purchase.setDel_status(false);
        purchase.setPur_tname(infoMap.getPur_tname());  // set the teacher name
        purchase.setClazz(infoMap.getClazz());
        Integer num = purchaseRepository.getAllPurNum(pid);  //judge the purchase total number
        num = num == null ? 0 : num;
        if (!infoMap.getApply_verify()) {
            return CommonResponseForm.of400("该申购未被审核");
        } else if (purchase.getPur_num() + num > infoMap.getApply_num()) {
            return CommonResponseForm.of400("采购总量超出申购数量");
        }

        purchaseRepository.saveAndFlush(purchase);
        return CommonResponseForm.of204("增加成功");
    }

    public Integer getAllPerNumByPId(String pid) {
        return purchaseRepository.getAllPurNum(pid);
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
                "采购日期",
                "采购人",
                "物料种类",
                "采购数量",
                "采购备注"
        };


        //set the width
        int[] widths = {20, 10, 16, 9, 18};
        for (int i = 0; i <= 4; i++)
            sheet.setColumnWidth(i, 256 * widths[i] + 184);
        String fileName = "purchase" + System.currentTimeMillis() + ".xls";//设置要导出的文件的名字

        /*

         */
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0=row0.createCell(0);
        cell0.setCellValue("采购单");
        CellUtil.setAlignment(cell0,HorizontalAlignment.CENTER_SELECTION);

        //单元格范围 参数（int firstRow, int lastRow, int firstCol, int lastCol)
        CellRangeAddress cellRangeAddress =new CellRangeAddress(0, 0, 0, 5);

        //在sheet里增加合并单元格
        sheet.addMergedRegion(cellRangeAddress);

        //新增数据行，并且设置单元格数据
        HSSFRow row = sheet.createRow(1);
        int rowNum = purchaseIds.length;

        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            CellUtil.setAlignment(cell,HorizontalAlignment.CENTER_SELECTION);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (int i = 2; i <= rowNum+1; i++) {
            HSSFRow r = sheet.createRow(i);
            HSSFCell cell2=r.createCell(0);
            HSSFCell cell3=r.createCell(1);
            HSSFCell cell4=r.createCell(2);
            HSSFCell cell5=r.createCell(3);
            HSSFCell cell6=r.createCell(4);
            cell2.setCellValue(purchases.get(i - 2).getPur_time());
            cell3.setCellValue(purchases.get(i - 2).getPur_tname());
            cell4.setCellValue(purchases.get(i - 2).getClazz());
            cell5.setCellValue(purchases.get(i - 2).getPur_num());
            cell6.setCellValue(purchases.get(i - 2).getPur_remark());

            CellUtil.setAlignment(cell2,HorizontalAlignment.CENTER_SELECTION);
            CellUtil.setAlignment(cell3,HorizontalAlignment.CENTER_SELECTION);
            CellUtil.setAlignment(cell4,HorizontalAlignment.CENTER_SELECTION);
            CellUtil.setAlignment(cell5,HorizontalAlignment.CENTER_SELECTION);
            CellUtil.setAlignment(cell6,HorizontalAlignment.CENTER_SELECTION);

        }
//);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public List<String> getClazzByTName(String tName) {
        return purchaseRepository.getClazzByTName(tName);
    }

    @Transactional
    public void delete2(String[] ids) {
        for (String id : ids) {
            purchaseRepository.delete2(id);
        }
    }


    @Transactional
    public CommonResponseForm updateNum(UpdateForm form) {
        Integer id = form.getId();
        Integer num = form.getNum();
        if (isExist(id)) {
            purchaseRepository.updateNum(id, num);
            return CommonResponseForm.of204("修改成功");
        } else {
            return CommonResponseForm.of400("不存在该采购项");
        }

    }

    private boolean isExist(Integer id) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        return optionalPurchase.isPresent();
    }
}
