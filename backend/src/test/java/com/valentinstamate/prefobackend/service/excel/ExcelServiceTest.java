package com.valentinstamate.prefobackend.service.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class ExcelServiceTest {

    private final String DEMO_EXCEL_PATH = "src/test/resources/test_sheet.xlsx";
    private final String DEMO_NEW_EXCEL_PATH = "src/test/resources/test_new_sheet.xlsx";

    @Test
    public void testReadingExcelFile() throws IOException {
        ExcelService excelService = new ExcelService();

        FileInputStream file = new FileInputStream(DEMO_EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(file);

        var data = excelService.readSheet(workbook, 0);

        for (var entry : data.entrySet()) {
            for (var item : entry.getValue()) {
                System.out.print(item + " ");
            }

            System.out.println("");
        }

        workbook.close();
    }

    @Test
    public void testCreatingExcelFile() throws IOException {
        ExcelService excelService = new ExcelService();

        FileInputStream file = new FileInputStream(DEMO_EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(file);

        var data = excelService.readSheet(workbook, 0);

        Workbook newWorkbook = excelService.createSheet(data, "Test Sheet");

        FileOutputStream outputStream = new FileOutputStream(DEMO_NEW_EXCEL_PATH);
        newWorkbook.write(outputStream);
        workbook.close();
    }

}