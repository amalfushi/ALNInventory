package aln.inventory;

import aln.Supply.Supply;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetWriter{
    
    public void writeSpreadhSheet(String excelFilePath, ArrayList<Supply> supplyBySuperCategory) throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        
        int rowCount = 0;
        
        createHeaderRow(sheet);
        
        for(Supply supply : supplyBySuperCategory){
            Row row = sheet.createRow(++rowCount);
            writeSupply(supply, row);
        }
        
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)){
            workbook.write(outputStream);
        } catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "The Excel file is open somewhere else!\nThe changes you've made could not be saved",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        workbook.close();
    }
    
    private void createHeaderRow(Sheet sheet) {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        Cell cellTitle = row.createCell(0);

        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("Category");

        Cell cellSubCategory = row.createCell(1);
        cellSubCategory.setCellStyle(cellStyle);
        cellSubCategory.setCellValue("Sub Category");

        Cell cellName = row.createCell(2);
        cellName.setCellStyle(cellStyle);
        cellName.setCellValue("Name");

        Cell cellOtherDetail = row.createCell(3);
        cellOtherDetail.setCellStyle(cellStyle);
        cellOtherDetail.setCellValue("Other Detail");

        Cell cellCurrentCount = row.createCell(4);
        cellCurrentCount.setCellStyle(cellStyle);
        cellCurrentCount.setCellValue("Curren Count");

        Cell cellDesiredCount = row.createCell(5);
        cellDesiredCount.setCellStyle(cellStyle);
        cellDesiredCount.setCellValue("Desired Count");

        Cell cellPrice = row.createCell(6);
        cellPrice.setCellStyle(cellStyle);
        cellPrice.setCellValue("Price");

        Cell cellVendor = row.createCell(7);
        cellVendor.setCellStyle(cellStyle);
        cellVendor.setCellValue("Vendor");
    }
    
    public void writeSupply(Supply supply, Row row){
        Cell cell = row.createCell(0);
        cell.setCellValue(supply.getCategory());
        
        cell = row.createCell(1);
        cell.setCellValue(supply.getSubCategory());
        
        cell = row.createCell(2);
        cell.setCellValue(supply.getName());
        
        cell = row.createCell(3);
        cell.setCellValue(supply.getOtherDetail());
        
        cell = row.createCell(4);
        cell.setCellValue(supply.getCurrentCount());
        
        cell = row.createCell(5);
        cell.setCellValue(supply.getDesiredCount());
        
        cell = row.createCell(6);
        cell.setCellValue(supply.getPrice());
        
        cell = row.createCell(7);
        cell.setCellValue(supply.getVendor());
    }
}
