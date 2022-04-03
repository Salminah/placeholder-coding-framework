package dataHandler;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataHandler {

//    public DataHandler(){
//
//    }

    public void writeDataToExcel(String dataPath, Object writeThis, int row, int column) throws IOException{

        File excelFile = new File(dataPath);
        FileInputStream file = new FileInputStream(excelFile);

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
//        CreationHelper createHelper = workbook.getCreationHelper();
//        sheet.getRow(row).getCell(column).setCellValue(writeThis.toString());
        sheet.createRow(row).createCell(column).setCellValue(writeThis.toString());
//        Row row = sheet.createRow(rowNum);
//        row.createCell(0).setCellValue(validateEmail);
//        row.createCell(1).setCellValue(emailValidationResults);


        FileOutputStream fileOutput = new FileOutputStream(excelFile);
        workbook.write(fileOutput);
        fileOutput.close();
        workbook.close();
    }
}
