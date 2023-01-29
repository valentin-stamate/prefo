package com.valentinstamate.prefobackend.service.excel.to_excel;

import com.valentinstamate.prefobackend.service.excel.ExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.*;

public class PreferenceRowsToExcelService {

    private static final List<Object> baseHeader = List.of(
            "Matricola", "Nume", "An", "Semestru", "Numar Semestru"
    );

    public static Workbook parse(List<List<Object>> rows, TreeMap<String, Integer> classesPerPackageMap) {
        var rowMap = new HashMap<Integer, List<Object>>();
        var currentRow = 0;

        var header = new ArrayList<>(baseHeader);

        var packageKeys = classesPerPackageMap.keySet();
        for (var packageName : packageKeys) {
            var classesNumber = classesPerPackageMap.get(packageName);

            for (int i = 1; i <= classesNumber; i++) {
                header.add(String.format("%s %d", packageName, i));
            }
        }

        rowMap.put(currentRow++, header);

        for (var rowValues : rows) {
            rowMap.put(currentRow++, rowValues);
        }

        return ExcelService.createSheet(rowMap, "Preferinte Studenti");
    }

}
