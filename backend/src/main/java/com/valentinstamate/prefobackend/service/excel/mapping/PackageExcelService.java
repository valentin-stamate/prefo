package com.valentinstamate.prefobackend.service.excel.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageExcelService extends ExcelRowService<PackageExcelRowMapping> {
    private final List<String> header = List.of(
            "Packet", "An", "Semestru", "Credite", "Discipline"
    );

    @Override
    public PackageExcelRowMapping of(List<Object> row) {
        var cols = new ArrayList<>(Collections.nCopies(header.size(), (Object) ""));

        for (int i = 0; i < row.size(); i++) {
            try {
                cols.set(i, row.get(i));
            } catch (Exception e) { }
        }

        return new PackageExcelRowMapping(
                cols.get(0),
                cols.get(1),
                cols.get(2),
                cols.get(3),
                cols.get(4)
        );
    }

    @Override
    public List<String> getHeader() {
        return new ArrayList<>(header);
    }
}
