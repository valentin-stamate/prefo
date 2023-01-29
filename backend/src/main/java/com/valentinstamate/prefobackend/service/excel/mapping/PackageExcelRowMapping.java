package com.valentinstamate.prefobackend.service.excel.mapping;

import com.valentinstamate.prefobackend.service.excel.ExcelService;

public class PackageExcelRowMapping {

    public final String packageName;
    public final Integer year;
    public final Integer semester;
    public final Integer credits;
    public final Integer classes;

    public PackageExcelRowMapping(Object packageName, Object year, Object semester, Object credits, Object classes) {
        this.packageName = (String) packageName;
        this.year = ExcelService.parseNumericCol(year);
        this.semester = ExcelService.parseNumericCol(semester);
        this.credits = ExcelService.parseNumericCol(credits);
        this.classes = ExcelService.parseNumericCol(classes);
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %d", this.packageName, this.year, this.semester, this.credits, this.classes);
    }
}
