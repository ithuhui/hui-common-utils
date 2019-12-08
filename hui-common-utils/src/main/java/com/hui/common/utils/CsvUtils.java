package com.hui.common.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>CsvUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/8 15:59.
 *
 * @author Gary.Hu
 */
public class CsvUtils {

    private static final CSVFormat csvFormat = CSVFormat.DEFAULT.withQuote(null);

    public static List<String> readRecord(File file, String[] headers) throws IOException {
        Reader in = new FileReader(file);
        List<CSVRecord> csvRecords = csvFormat.parse(in).getRecords();
        List<String> records = csvRecords.stream().map(x -> x.getComment()).collect(Collectors.toList());
        return records;
    }
}
