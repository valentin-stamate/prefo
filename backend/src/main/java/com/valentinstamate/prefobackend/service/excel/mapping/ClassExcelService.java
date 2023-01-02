package com.valentinstamate.prefobackend.service.excel.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassExcelService extends ExcelRowService<ClassExcelRowMapping> {
    private final List<String> header = List.of(
            "Prescurtare", "Nume", "An", "Semestru", "Titular", "Site", "Packet", "Credite"
    );

    @Override
    public ClassExcelRowMapping of(List<Object> row) {
        var cols = new ArrayList<>(Collections.nCopies(header.size(), (Object) ""));

        for (int i = 0; i < row.size(); i++) {
            try {
                cols.set(i, row.get(i));
            } catch (Exception e) { }
        }

        return new ClassExcelRowMapping(
                cols.get(0),
                cols.get(1),
                cols.get(2),
                cols.get(3),
                cols.get(4),
                cols.get(5),
                cols.get(6),
                cols.get(7),
                cols.get(8)
        );
    }

    @Override
    public List<String> getHeader() {
        return new ArrayList<>(header);
    }
}
