package org.RobustFramework.Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtils {

    public static List<Map<String, String>> getTestData(String filePath, String sheetName) throws IOException {

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        List<Map<String, String>> data = new ArrayList<>();

        Row headerRow = sheet.getRow(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = headerRow.getPhysicalNumberOfCells();

        for (int i = 1; i < rowCount; i++) {
            Row currentRow = sheet.getRow(i);
            Map<String, String> rowData = new HashMap<>();

            for (int j = 0; j < colCount; j++) {
                String key = headerRow.getCell(j).getStringCellValue();
                String value = getCellValue(currentRow.getCell(j));

                rowData.put(key, value);
            }

            data.add(rowData);
        }

        workbook.close();
        fis.close();

        return data;
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case BLANK:
                return "";

            default:
                return "";
        }
    }
}