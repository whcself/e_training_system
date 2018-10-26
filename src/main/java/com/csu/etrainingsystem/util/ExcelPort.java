package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.student.entity.Student;
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

    /**
     * -ScJn 2018.10.26
     * @param path the path of the excel file
     * @return the students list
     */
    public static ArrayList<Student> readExcel(String path) {
        ArrayList<Student> students=new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            DecimalFormat decimalFormat=new DecimalFormat();
            Row firstRow=sheet.getRow(0);
            ArrayList<String> colName=new ArrayList<>();
            for(Cell cell:firstRow) colName.add(cell.getStringCellValue());

            //Iterate through each rows one by one
            for (Row row : sheet) {
                if (row.getRowNum()==0)continue;
                        //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int cell_index=-1;
                Student student=new Student();
                while (cellIterator.hasNext()) {
                    cell_index++;
                    Cell cell = cellIterator.next();
                    String cell_colName=colName.get(cell_index);
                    System.out.print(colName+"\t");
                    //Check the cell type and format accordingly
                    if (cell.getCellType()==CellType.STRING) {
                        String value=cell.getStringCellValue();
                        System.out.print(value+"\t");
                        setPro(cell_colName,value,student);
                    }

                    else if(cell.getCellType()==CellType.NUMERIC){
                        CellStyle style=cell.getCellStyle();
                        double value=cell.getNumericCellValue();
                        DecimalFormat format=new DecimalFormat();
                        String temp=style.getDataFormatString();
                        if(temp.equals("General")){
                            format.applyPattern("#");
                        }
                        String value2=format.format(value);
                        setPro(cell_colName,value2,student);
                        System.out.print(value2+"\t");
                    }
                    else if(cell.getCellType()==CellType._NONE)
                        System.out.print("null"+ "\t");
                }
                students.add(student);
                System.out.println("");
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * for set value to corresponding property(attribute)
     * @param cell_colName recent column name
     * @param value recent cell value
     * @param student recent student object
     */
    private static void setPro(String cell_colName, String value, Student student){
        switch (cell_colName){
            case "姓名": student.setName(value);break;
            case "学号": student.setStuId(value);break;
            case "专业": student.setMajor(value);break;
            case "班级": student.settClass(value);break;
            case "批次": student.setBatchId(value);break;
            case "组号": student.setTeamId(value);break;
            case "学院": student.setAcademy(value);break;
        }
    }
}
