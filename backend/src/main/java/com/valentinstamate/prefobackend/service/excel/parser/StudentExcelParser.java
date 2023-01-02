package com.valentinstamate.prefobackend.service.excel.parser;

import com.valentinstamate.prefobackend.service.excel.mapping.ExcelRowService;
import com.valentinstamate.prefobackend.service.excel.mapping.StudentExcelRowMapping;
import com.valentinstamate.prefobackend.service.excel.mapping.StudentExcelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentExcelParser extends ExcelParser<StudentExcelRowMapping> {

    @Inject
    private StudentExcelService studentExcelService;

    @Override
    public ExcelRowService getExcelService() {
        return studentExcelService;
    }


}
