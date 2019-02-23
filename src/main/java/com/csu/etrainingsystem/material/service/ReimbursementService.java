package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.entity.Reimbursement;
import com.csu.etrainingsystem.material.form.ReimAddForm;
import com.csu.etrainingsystem.material.form.UpdateForm;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.material.repository.ReimbursementRepository;
import com.csu.etrainingsystem.util.TimeUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
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
                                       String purchaseId) {

        return reimbursementRepository.findBy6(begin, end, tName, clazz, verify, purchaseId);
    }

    public List<String> getClazzByTName2(String tName) {
        return purchaseRepository.getClazzByTName(tName);
    }

    /**
     * @param flag true all, false 报账单
     * @throws IOException
     */
    public void downloadReim(HttpServletResponse response,
                             List<String> reimIds, boolean flag) throws IOException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        for (String key : reimIds) {
            reimbursements.add(reimbursementRepository.findById(key));
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("报账单");
        String[] headers;
        if (flag) {
            headers = new String[]{
                    "申购编号",
                    "新增报账申请日期",
                    "采购人",
                    "物料种类",
                    "报账数量",
                    "审核状态",
                    "审核人",
                    "报账备注"
            };

            //新增数据行，并且设置单元格数据
            HSSFRow row = sheet.createRow(0);
            int rowNum = reimIds.size();

            //在excel表中添加表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

        } else {
            headers = new String[]{
                    "新增报账申请日期",
                    "采购人",
                    "物料种类",
                    "报账数量",
                    "审核人",
                    "报账备注"
            };
            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0=row0.createCell(0);
            cell0.setCellValue("报账单");
            CellUtil.setAlignment(cell0,HorizontalAlignment.CENTER_SELECTION);
            CellRangeAddress cellRangeAddress =new CellRangeAddress(0, 0, 0, 5);
            sheet.addMergedRegion(cellRangeAddress);

            //新增数据行，并且设置单元格数据
            HSSFRow row = sheet.createRow(1);

            //在excel表中添加表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                CellUtil.setAlignment(cell,HorizontalAlignment.CENTER);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            int[] widths = {20, 10, 16, 9,10, 18};
            for (int i = 0; i <= 5; i++)
                sheet.setColumnWidth(i, 256 * widths[i] + 184);

        }
        String fileName = "purchase" + TimeUtil.getNowDate() + ".xls";//设置要导出的文件的名字

        int rowNum = reimIds.size();

        if (flag) {
            for (int i = 1; i <= rowNum; i++) {
                HSSFRow r = sheet.createRow(i);
                r.createCell(0).setCellValue(reimbursements.get(i - 1).getPurchase_id());
                r.createCell(1).setCellValue(reimbursements.get(i - 1).getRemib_time().substring(0,16));
                r.createCell(2).setCellValue(reimbursements.get(i - 1).getPur_tname());
                r.createCell(3).setCellValue(reimbursements.get(i - 1).getClazz());
                r.createCell(4).setCellValue(reimbursements.get(i - 1).getRemib_num());
                r.createCell(5).setCellValue(reimbursements.get(i - 1).getRemib_vertify());
                r.createCell(6).setCellValue(reimbursements.get(i - 1).getRemib_vert_tname());
                r.createCell(7).setCellValue(reimbursements.get(i - 1).getRemib_remark());
            }
        } else {
            for (int i = 2; i <= rowNum+1; i++) {
                HSSFRow r = sheet.createRow(i);
                HSSFCell cell2=r.createCell(0);
                HSSFCell cell3=r.createCell(1);
                HSSFCell cell4=r.createCell(2);
                HSSFCell cell5=r.createCell(3);
                HSSFCell cell6=r.createCell(4);
                HSSFCell cell7=r.createCell(5);
                cell2.setCellValue(reimbursements.get(i - 2).getRemib_time().substring(0,16));//截取到分钟
                cell3.setCellValue(reimbursements.get(i - 2).getPur_tname());
                cell4.setCellValue(reimbursements.get(i - 2).getClazz());
                cell5.setCellValue(reimbursements.get(i - 2).getRemib_num());
                String vert_tname=reimbursements.get(i - 2).getRemib_vert_tname();
                if(vert_tname==null){
                    cell6.setCellValue("未审核");

                }
                else{
                    cell6.setCellValue(vert_tname);
                }
                cell7.setCellValue(reimbursements.get(i - 2).getRemib_remark());

                CellUtil.setAlignment(cell2,HorizontalAlignment.CENTER);
                CellUtil.setAlignment(cell3,HorizontalAlignment.CENTER);
                CellUtil.setAlignment(cell4,HorizontalAlignment.CENTER);
                CellUtil.setAlignment(cell5,HorizontalAlignment.CENTER);
                CellUtil.setAlignment(cell6,HorizontalAlignment.CENTER);
                CellUtil.setAlignment(cell7,HorizontalAlignment.CENTER);
            }
        }

//);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    private Integer getAllPurNum(String pid) {
        Integer pn = purchaseRepository.getAllPurNum(pid);
        pn = pn == null ? 0 : pn;
        return pn;
    }

    private Integer getAllReimNum(String pid) {
        Integer rn = reimbursementRepository.getSumReimByPId(pid);

        rn = rn == null ? 0 : rn;
        return rn;
    }

    public CommonResponseForm add(ReimAddForm form) {
        Reimbursement reim = new Reimbursement();
        String pid = form.getPurchaseId();
        Integer num = form.getNum();
        //todo 查询该pid的当前报账总数
        Integer pn = getAllPurNum(pid);
        Integer rn = getAllReimNum(pid);

        if (num + rn > pn) {
            return CommonResponseForm.of400("报账超过总数");
        }
        String remark = form.getRemark();
        reim.setPurchase_id(pid);
        reim.setRemib_num(num);
        reim.setRemib_remark(remark);
        reim.setClazz(purchaseRepository.getClazzByPId(pid));
        reim.setDel_status(false);
        reim.setRemib_vertify(false);
        reim.setPur_tname(purchaseRepository.getPurTNameByPId(pid));
        reim.setRemib_time(TimeUtil.getNowTime());
        reimbursementRepository.saveAndFlush(reim);
        return CommonResponseForm.of204("增加成功");
    }

    public int remain(String pid) {
        return getAllPurNum(pid) - getAllReimNum(pid);
    }

    @Transactional
    public void verifyReim(String id, String name, Integer num) {
        reimbursementRepository.verifyReim(TimeUtil.getNowTime(), name, id);
        if (num != null) {
            Reimbursement r = reimbursementRepository.findById(id);
            r.setRemib_num(num);
            reimbursementRepository.saveAndFlush(r);
        }
    }

    public Integer getAllReimbNumByPId(String pid) {
        return reimbursementRepository.getAllReimbNum(pid);
    }

    @Transactional
    public void delete(String[] ids) {
        for (String id : ids) {
            reimbursementRepository.delete2(id);
        }
    }


}
