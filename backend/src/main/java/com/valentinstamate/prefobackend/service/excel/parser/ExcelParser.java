package com.valentinstamate.prefobackend.service.excel.parser;

import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.service.excel.ExcelService;
import com.valentinstamate.prefobackend.service.excel.mapping.ExcelRowService;
import com.valentinstamate.prefobackend.service.excel.mapping.StudentExcelRowMapping;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ExcelParser<T> {

    public List<T> parse(byte[] buffer) throws Exception {
        var fileInputStream = new ByteArrayInputStream(buffer);
        var workBook = new XSSFWorkbook(fileInputStream);
        var excelService = this.getExcelService();

        var data = ExcelService.readSheet(workBook, 0);

        var headers = data.get(0);

        if (!excelService.checkHeader(headers)) {
            throw new Exception(ResponseMessage.INVALID_EXCEL_HEADER);
        }

        /* Remove the header */
        data.remove(0);
        data = ExcelService.filterInvalidRows(data);

        return (List<T>) data.values().stream().map(excelService::of).collect(Collectors.toList());
    }

    abstract ExcelRowService getExcelService();
}
