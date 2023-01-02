package com.valentinstamate.prefobackend.service.excel.parser;

import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.service.excel.ExcelService;
import com.valentinstamate.prefobackend.service.excel.mapping.StudentExcelRowMapping;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentExcelParser implements ExcelParser<StudentExcelRowMapping> {
    @Override
    public List<StudentExcelRowMapping> parse(byte[] buffer) throws Exception {
        var fileInputStream = new ByteArrayInputStream(buffer);
        var workBook = new XSSFWorkbook(fileInputStream);

        var data = ExcelService.readSheet(workBook, 0);

        var headers = data.get(0);

        if (!StudentExcelRowMapping.checkHeader(headers)) {
            throw new Exception(ResponseMessage.INVALID_EXCEL_HEADER);
        }

        /* Remove the header */
        data.remove(0);
        data = ExcelService.filterInvalidRows(data);

        return data.values().stream().map(StudentExcelRowMapping::of).collect(Collectors.toList());
    }
}
