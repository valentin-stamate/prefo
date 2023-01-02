package com.valentinstamate.prefobackend.service.excel.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentExcelService extends ExcelRowService<StudentExcelRowMapping> {

    private final List<String> header = List.of(
            "Numar", "Nume", "Matricol", "Email"
    );

    @Override
    public StudentExcelRowMapping of(List<Object> row) {
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
    public List<String> getHeader() {
        return new ArrayList<>(header);
    }

}
