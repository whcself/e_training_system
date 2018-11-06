package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import com.csu.etrainingsystem.student.service.StudentGroupService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * for reading the Excel file
 */
@Component
public class ExcelPort {


    private static StudentGroupService studentGroupService;

    @Autowired
    public ExcelPort(StudentGroupService studentGroupService) {
        ExcelPort.studentGroupService = studentGroupService;
    }


    public static void main(String[] args) {
        readExcel("test.xlsx", "2", 8);
    }

    /**
     * -ScJn 2018.10.26
     *
     * @param path      the path of the excel file
     * @param batchName 2018S101/2018S201/2018S501
     * @return the students list
     * <p>
     * 2018 11.3 update:
     * 增加批次，组数参数，导入excel时确定批次和组数
     */
    public static ArrayList<Student> readExcel(String path, String batchName, int groupNum) {
        String[] groupName25 = {"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3",
                "E1", "E2", "E3", "F1", "F2", "F3", "G1", "G2", "G3"};
        String[] groupName1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int[] numOfAdd = new int[20];
        int[] numOfGroup1 = new int[20];
        String[] groupName = batchName.charAt(5) == '1' ? groupName1 : groupName25;
        ArrayList<Student> students = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            //人数多一个的group数
            int exGroupNum = rowNum % groupNum;
            int stuNum = rowNum / groupNum;
            for (int i = 0; i < groupNum; i++) {

                if (i < exGroupNum) {
                    numOfGroup1[i] = stuNum + 1;
                } else numOfGroup1[i] = stuNum;
                studentGroupService.addStudentGroup(
                        new StudentGroup(new StudentGroupId(groupName[i], batchName), numOfGroup1[i], false)
                );
                if (i != 0)
                    numOfAdd[i] = numOfAdd[i - 1] + numOfGroup1[i];
                else numOfAdd[i] = numOfGroup1[i];
                System.out.println(i + " " + numOfGroup1[i] + " " + numOfAdd[i]);
            }
            System.out.println("rowNum:" + rowNum);
            DecimalFormat decimalFormat = new DecimalFormat();
            Row firstRow = sheet.getRow(0);
            ArrayList<String> colName = new ArrayList<>();
            for (Cell cell : firstRow) colName.add(cell.getStringCellValue());

            //Iterate through each rows one by one
            int groupIndex = 0;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int cell_index = -1;
                Student student = new Student();
                student.setBatch_name(batchName);
                while (cellIterator.hasNext()) {
                    cell_index++;
                    Cell cell = cellIterator.next();
                    String cell_colName = colName.get(cell_index);
//                    System.out.print(colName + "\t");
                    //Check the cell type and format accordingly

                    //进入到一个cell，判断string还是numeric
                    if (cell.getCellType() == CellType.STRING) {
                        String value = cell.getStringCellValue();
//                        System.out.print(value + "\t");
                        setPro(cell_colName, value, student);
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        CellStyle style = cell.getCellStyle();
                        double value = cell.getNumericCellValue();
                        DecimalFormat format = new DecimalFormat();
                        String temp = style.getDataFormatString();
                        if (temp.equals("General")) {
                            format.applyPattern("#");
                        }
                        String value2 = format.format(value);
                        setPro(cell_colName, value2, student);
                    } else if (cell.getCellType() == CellType._NONE)
                        System.out.print("null" + "\t");
                }// 一行结束
                int rowIndex = row.getRowNum();
                if (rowIndex <= numOfAdd[groupIndex]) {
                    student.setS_group_id(groupName[groupIndex]);
                } else {

                    groupIndex++;
                    student.setS_group_id(groupName[groupIndex]);
                }

                students.add(student);
                System.out.println("student index：" + rowIndex + '\t' + "groupid:" + student.getS_group_id());
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * for set value to corresponding property(attribute)
     *
     * @param cell_colName recent column name
     * @param value        recent cell value
     * @param student      recent student object
     */
    private static void setPro(String cell_colName, String value, Student student) {
        switch (cell_colName) {

            case "姓名":
                student.setSname(value);
                break;
            case "学号":
                student.setSid(value);
                break;
            case "专业":
                student.setSdept(value);
                break;
            case "班级":
                student.setClazz(value);
                break;
            case "批次":
                student.setBatch_name(value);
                break;
            case "组号":
                student.setS_group_id(value);
                break;
            case "学院":
                student.setDepart(value);
                break;


        }
    }
}
