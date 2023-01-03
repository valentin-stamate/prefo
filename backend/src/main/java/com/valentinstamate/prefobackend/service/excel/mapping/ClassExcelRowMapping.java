package com.valentinstamate.prefobackend.service.excel.mapping;

import com.valentinstamate.prefobackend.service.excel.ExcelService;

public class ClassExcelRowMapping {

    public final String code;
    public final String shortName;
    public final String name;
    public final Integer year;
    public final Integer semester;
    public final String owner;
    public final String site;
    public final String classPackage;
    public final Integer credits;

    public ClassExcelRowMapping(Object code, Object shortName, Object name, Object year, Object semester, Object owner, Object site, Object classPackage, Object credits) {
        this.code = (String) code;
        this.shortName = (String) shortName;
        this.name = (String) name;
        this.year = ExcelService.parseNumericCol(year);
        this.semester = ExcelService.parseNumericCol(semester);
        this.owner = (String) owner;
        this.site = (String) site;
        this.classPackage = (String) classPackage;
        this.credits = ExcelService.parseNumericCol(credits);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d %d %s %s %s %d",
                this.code, this.shortName, this.name, this.year, this.semester, this.owner,
                this.site, this.classPackage, this.credits);
    }

}
