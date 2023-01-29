package com.valentinstamate.prefobackend.service.excel.to_excel;

import com.valentinstamate.prefobackend.service.excel.ExcelService;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.List;

public class PreferenceRowsToExcelService {

    private static final List<Object> header = List.of(
            "Matricola", "Nume", "An", "Semestru", "Numar Semestru",
            "CO1 1", "CO1 2", "CO1 3", "CO1 4",
            "CO2 1", "CO2 2", "CO2 3", "CO2 4",
            "CO3 1", "CO3 2", "CO3 3", "CO3 4",
            "CO4 1", "CO4 2", "CO4 3", "CO4 4",
            "CO5 1", "CO5 2", "CO5 3", "CO5 4",
            "CO6 1", "CO6 2", "CO6 3", "CO6 4",
            "CO7 1", "CO7 2", "CO7 3", "CO7 4"
    );

    public static Workbook parse(List<List<Object>> rows) {
        var rowMap = new HashMap<Integer, List<Object>>();
        var currentRow = 1;

        rowMap.put(currentRow++, header);

        for (var rowValues : rows) {
            rowMap.put(currentRow++, rowValues);
        }

        return ExcelService.createSheet(rowMap, "Preferinte Studenti");
    }

}
