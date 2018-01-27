package aln.inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetReader {
    
    public ArrayList<String> readSpreadsheet(String excelFilePath) throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        
        ArrayList allCells = new ArrayList();
        
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
       
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        allCells.add(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        allCells.add(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        allCells.add(cell.getNumericCellValue());
                        break;
                }
            }
        }
        workbook.close();
        inputStream.close();
        return allCells;
    }
}
