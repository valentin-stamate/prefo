package com.valentinstamate.prefobackend.service.excel.mapping;

import java.util.List;
import java.util.Objects;

public abstract class ExcelRowService<T> {
    public abstract T of(List<Object> row);
    public abstract List<String> getHeader();

    public boolean checkHeader(List<Object> givenHeader) {
        List<String> header = getHeader();

        if (givenHeader.size() != header.size()) {
            return false;
        }

        for (int i = 0; i < givenHeader.size(); i++) {
            if (!Objects.equals(header.get(i), givenHeader.get(i))) {
                return false;
            }
        }

        return true;
    }


}
