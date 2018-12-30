package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import com.csu.etrainingsystem.student.service.StudentGroupService;

import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


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
    private static UserService userService;

    @Autowired
    public ExcelPort(UserService userService,StudentGroupService studentGroupService) {
        ExcelPort.userService =userService;
        ExcelPort.studentGroupService = studentGroupService;
    }


//    public static void main(String[] args) {
//        System.out.println(readExcel(new File("test.xlsx"), "2"));
//    }

    /**
     * -ScJn 2018.10.26
     *
     * @param inFile   he path of the excel file
     * @param batchName 2018S101/2018S201/2018S501
     * @return the students list
     * <p>
     * 2018 11.3 update:
     * 增加批次，组数参数，导入excel时确定批次和组数
     */
    public static ArrayList<Student> readExcel(MultipartFile inFile, String batchName) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            //scjn need learn
            String fileName=inFile.getOriginalFilename();
            System.out.println("fileName:"+fileName);
//            File convFile = new File( inFile.getOriginalFilename());
//            inFile.transferTo(convFile);

//            FileInputStream file = new FileInputStream(convFile);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(inFile.getInputStream());

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            //人数多一个的group数

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
                students.add(student);
            }
//            file.close();
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
            case "密码":
//                setPass(student,value);


        }
    }
//    private static void setPass(Student student,String pwd){
//        User
//
//
//    }
}
