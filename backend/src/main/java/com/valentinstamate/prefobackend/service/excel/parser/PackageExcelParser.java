package com.valentinstamate.prefobackend.service.excel.parser;

import com.valentinstamate.prefobackend.service.excel.mapping.ExcelRowService;
import com.valentinstamate.prefobackend.service.excel.mapping.PackageExcelRowMapping;
import com.valentinstamate.prefobackend.service.excel.mapping.PackageExcelService;
import jakarta.inject.Inject;

public class PackageExcelParser extends ExcelParser<PackageExcelRowMapping> {

    @Inject private PackageExcelService packageExcelService;

    @Override
    ExcelRowService getExcelService() {
        return packageExcelService;
    }
}
