package com.valentinstamate.prefobackend.service.excel.mapping;

import com.valentinstamate.prefobackend.service.excel.ExcelService;

public class StudentExcelRowMapping {

    public final Integer number;
    public final String name;
    public final String identifier;
    public final String email;
    public final Integer year;
    public final Integer semester;

    public StudentExcelRowMapping(Object number, Object name, Object identifier, Object email, Object year, Object semester) {
        this.number = ExcelService.parseNumericCol(number);
        this.name = (String) name;
        this.identifier = (String) identifier;
        this.email = (String) email;
        this.year = ExcelService.parseNumericCol(year);
        this.semester = ExcelService.parseNumericCol(semester);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %d $d", this.number, this.name, this.identifier, this.email, this.year, this.semester);
    }
}
