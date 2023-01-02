package com.valentinstamate.prefobackend.service.excel.parser;

import com.valentinstamate.prefobackend.service.excel.mapping.ClassExcelRowMapping;
import com.valentinstamate.prefobackend.service.excel.mapping.ClassExcelService;
import com.valentinstamate.prefobackend.service.excel.mapping.ExcelRowService;
import jakarta.inject.Inject;

public class ClassExcelParser extends ExcelParser<ClassExcelRowMapping> {

    @Inject
    private ClassExcelService classExcelService;

    @Override
    public ExcelRowService getExcelService() {
        return classExcelService;
    }
}
