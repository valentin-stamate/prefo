package com.valentinstamate.prefobackend.service.excel.mapping;

import com.valentinstamate.prefobackend.service.excel.ExcelService;

public class StudentExcelRowMapping {

    public final Integer number;
    public final String name;
    public final String identifier;
    public final String email;

    public StudentExcelRowMapping(Object number, Object name, Object identifier, Object email) {
        this.number = ExcelService.parseNumericCol(number);
        this.name = (String) name;
        this.identifier = (String) identifier;
        this.email = (String) email;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", this.number, this.name, this.identifier, this.email);
    }
}
