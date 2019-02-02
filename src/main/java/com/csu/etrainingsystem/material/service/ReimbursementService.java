package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.entity.Reimbursement;
import com.csu.etrainingsystem.material.form.ReimAddForm;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.material.repository.ReimbursementRepository;
import com.csu.etrainingsystem.util.TimeUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementService {
    private final ReimbursementRepository reimbursementRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository, PurchaseRepository purchaseRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.purchaseRepository = purchaseRepository;
    }



    public List<Reimbursement> findBy6(String begin,
                                       String end,
                                       String tName,
                                       String clazz,
                                       String verify,
                                       String purchaseId){

        return reimbursementRepository.findBy6(begin,end,tName,clazz,verify,purchaseId);
    }

    public List<String> getClazzByTName2(String tName){
        return purchaseRepository.getClazzByTName(tName);
    }

    public void downloadReim(HttpServletResponse response,
                             List<String> reimIds) throws IOException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        for (String key : reimIds) {
            reimbursements.add(reimbursementRepository.findById(key));
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("报账单");
        String[] headers = {
                "申购编号",
                "新增报账申请日期",
                "采购人",
                "物料种类",
                "报账数量",
                "审核状态",
                "审核人",
                "报账备注"
        };
        String fileName = "purchase" + System.currentTimeMillis() + ".xls";//设置要导出的文件的名字

        //新增数据行，并且设置单元格数据
        HSSFRow row = sheet.createRow(0);
        int rowNum = reimIds.size();

        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (int i = 1; i <= rowNum; i++) {
            HSSFRow r = sheet.createRow(i);
            r.createCell(0).setCellValue(reimbursements.get(i - 1).getPurchase_id());
            r.createCell(1).setCellValue(reimbursements.get(i - 1).getRemib_time());
            r.createCell(2).setCellValue(reimbursements.get(i - 1).getPur_tname());
            r.createCell(3).setCellValue(reimbursements.get(i - 1).getClazz());
            r.createCell(4).setCellValue(reimbursements.get(i - 1).getRemib_num());
            r.createCell(5).setCellValue(reimbursements.get(i - 1).getRemib_vertify());
            r.createCell(6).setCellValue(reimbursements.get(i - 1).getRemib_vert_tname());
            r.createCell(7).setCellValue(reimbursements.get(i - 1).getRemib_remark());
        }
//);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


    public CommonResponseForm add(ReimAddForm form){
        Reimbursement reim=new Reimbursement();
        String pid=form.getPurchaseId();
        int num=form.getNum();
        //todo 查询该pid的当前报账总数
        if(num+reimbursementRepository.getSumReimByPId(pid)>purchaseRepository.getAllPurNum(pid)){
            return CommonResponseForm.of400("报账超过总数");
        }
        String remark=form.getRemark();
        reim.setPurchase_id(pid);
        reim.setRemib_num(num);
        reim.setRemib_remark(remark);
        reim.setClazz(purchaseRepository.getClazzByPId(pid));
        reim.setDel_status(false);
        reim.setRemib_vertify(false);
        reim.setPur_tname(purchaseRepository.getPurTNameByPId(pid));
        reim.setRemib_time(TimeUtil.getNowDate());
        reimbursementRepository.saveAndFlush(reim);
        return CommonResponseForm.of204("增加成功");
    }

    public int remain(String pid){
        return purchaseRepository.getAllPurNum(pid)-reimbursementRepository.getSumReimByPId(pid);
    }

    @Transactional
    public void verifyReim(String id,String name){
        reimbursementRepository.verifyReim(TimeUtil.getNowDate(),name,id);
    }

    public Integer getAllReimbNumByPId(String pid){
        return   reimbursementRepository.getAllReimbNum (pid);
    }

    @Transactional
    public void delete(String[] ids){
        for(String id:ids){
            reimbursementRepository.delete2(id);
        }
    }
}
