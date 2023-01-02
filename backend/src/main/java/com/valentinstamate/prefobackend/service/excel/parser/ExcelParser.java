package com.valentinstamate.prefobackend.service.excel.parser;

import java.io.IOException;
import java.util.List;

public interface ExcelParser<T> {
    List<T> parse(byte[] buffer) throws Exception;
}
