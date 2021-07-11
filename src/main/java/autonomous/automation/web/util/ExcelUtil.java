/*
 * Liquid Pay
 */
package autonomous.automation.web.util;

import autonomous.automation.web.util.excel.ToHtmlConverter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static autonomous.automation.web.util.excel.ToHtmlConverter.create;


public class ExcelUtil {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;

    public static void convertExcelToHtml(String excelFile, String htmlOuput) throws Exception {
        File file = new File(htmlOuput);
        FileWriter fileWriter = new FileWriter(file);
        ToHtmlConverter toHtml = ToHtmlConverter.create(excelFile, new PrintWriter(fileWriter));
        toHtml.setCompleteHTML(true);
        toHtml.printPage();
    }

    public static void writeDataToExcel(Object[][] args, String filePath, String sheetName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        int rowCount = 0;
        for (Object[] data : args) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : data) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

    }

    public static Object[][] getDataFromExl(String testDataFile, String sheetName) throws Exception {
        List<List<String>> data = new ArrayList();

        FileInputStream file = new FileInputStream(new File(testDataFile));
        //Create Workbook instance holding reference to .xlsx file
        Workbook workbook = WorkbookFactory.create(file);
        //Get first/desired sheet from the workbook
        Sheet sheet = workbook.getSheet(sheetName);
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        System.out.println("SheetName: " + sheet.getSheetName());
        boolean flagEof = false;
        while (rowIterator.hasNext() & flagEof == false) {
            Row row = rowIterator.next();
            try {
                if (!row.getCell(0).getStringCellValue().equalsIgnoreCase("eof")) {
                    List<String> dataRow = new ArrayList<String>();
                    int colCount = row.getLastCellNum();
                    for (int i = 0; i < colCount; i++) {
                        if (row.getCell(i) == null) {
                            dataRow.add("");
                        } else {
                            String str;
                            if (row.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                str = String.valueOf(row.getCell(i).getNumericCellValue()).replace(".0","");
                            }
                            else {
                                str = row.getCell(i).getStringCellValue();
                            }
                            dataRow.add(str);
                        }
                    }
                    data.add(dataRow);
                } else {
                    flagEof = true;
                }
            }catch (Exception ex){
                if (!row.getCell(0).toString().equalsIgnoreCase("eof")) {
                    List<String> dataRow = new ArrayList<String>();
                    int colCount = row.getLastCellNum();
                    for (int i = 0; i < colCount; i++) {
                        if (row.getCell(i) == null) {
                            dataRow.add("");
                        } else {
                            String str;
                            long value;
                            if (row.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                                str = String.valueOf(row.getCell(i).getNumericCellValue());
                                value = (long)row.getCell(i).getNumericCellValue();
                                str = String.valueOf(value);
                            } else {
                                str = row.getCell(i).getStringCellValue();
                            }
                            dataRow.add(str);
                        }
                    }
                    data.add(dataRow);
                } else {
                    flagEof = true;
                }
            }
        }
        return convertListToArray(data);
    }

    public static Object[][] convertListToArray(List<List<String>> data) {
        Object[][] retObjArr = new Object[data.size()-1][data.get(0).size()];
        for (int i = 1; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                retObjArr[i-1][j] = data.get(i).get(j);
            }
        }
        return retObjArr;
    }

    public static Path setExcelFile(String Path, String SheetName) throws Exception {
        try {
            FileInputStream ExcelFile = new FileInputStream(Path);

            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);

            return (java.nio.file.Path) ExcelFile;

        } catch (Exception e){
            throw (e);
        }

    }
}
