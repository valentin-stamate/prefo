package com.valentinstamate.prefobackend.service.excel.mapping;

import com.valentinstamate.prefobackend.service.excel.ExcelService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentExcelRowMapping {

    private static final List<String> header = List.of(
            "Numar", "Nume", "Matricol", "Email"
    );

    public final Integer number;
    public final String name;
    public final String identifier;
    public final String email;

    private StudentExcelRowMapping(Object number, Object name, Object identifier, Object email) {
        this.number = ExcelService.parseNumericCol(number);
        this.name = (String) name;
        this.identifier = (String) identifier;
        this.email = (String) email;
    }

    public static List<String> getHeader() {
        return new ArrayList<>(header);
    }

    public static boolean checkHeader(List<Object> header) {
        if (header.size() != StudentExcelRowMapping.header.size()) {
            return false;
        }

        for (int i = 0; i < header.size(); i++) {
            if (!Objects.equals(header.get(i), StudentExcelRowMapping.header.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static StudentExcelRowMapping of(List<Object> row) {
        var cols = new ArrayList<>(Collections.nCopies(header.size(), (Object) ""));

        for (int i = 0; i < row.size(); i++) {
            try {
                cols.set(i, row.get(i));
            } catch (Exception e) { }
        }

        return new StudentExcelRowMapping(
                cols.get(0),
                cols.get(1),
                cols.get(2),
                cols.get(3)
        );
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", this.number, this.name, this.identifier, this.email);
    }
}
