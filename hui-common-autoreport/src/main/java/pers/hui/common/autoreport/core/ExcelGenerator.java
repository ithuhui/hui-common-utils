package pers.hui.common.autoreport.core;

import pers.hui.common.autoreport.model.ExcelData;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <b><code>ExcelGenerator</code></b>
 * <p/>
 * Description:
 * <p/>
 * <b>Creation Time:</b> 2018/12/6 1:05.
 *
 * @author HuWeihui
 */
public enum ExcelGenerator {
    /**
     * init single
     */
    INSTANCE;

    @AllArgsConstructor
    enum ExcelType {
        /**
         * EXCEL类型
         */
        XLSX(".xlsx", new SXSSFWorkbook()),
        XLS(".xls", new HSSFWorkbook());
        private String suffix;
        private Workbook workbook;
    }

    public File gen(String fileName, String targetDir, String sheetName, ExcelData excelData, String excelType) throws IOException {
        ExcelType excelStrategy = ExcelType.valueOf(excelType.toUpperCase());

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        Workbook workbook = excelStrategy.workbook;
        fileName = fileName + excelStrategy.suffix;

        File targetFile = getTargetFile(fileName, targetDir);

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                Workbook xlsxWorkBook = createWorkbook(workbook, sheetName, excelData)
        ) {
            xlsxWorkBook.write(fileOutputStream);
        }

        return targetFile;
    }

    private File getTargetFile(String fileName, String targetDir) {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = targetDir.concat(File.separator).concat(fileName);
        return new File(filePath);
    }

    private Workbook createWorkbook(Workbook workbook, String sheetName, ExcelData excelData) {
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = workbook.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        Row row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        CellStyle style = workbook.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        //创建标题
        createTitle(row, style, excelData);

        //创建内容
        createContent(sheet, style, excelData);
        return workbook;
    }


    private void createTitle(Row row, CellStyle cellStyle, ExcelData excelData) {
        List<String> title = excelData.getTitles();
        for (int i = 0; i < title.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(cellStyle);
        }
    }

    private void createContent(Sheet sheet, CellStyle cellStyle, ExcelData excelData) {
        List<List<String>> values = excelData.getValues();
        for (int i = 0; i < values.size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < values.get(i).size(); j++) {
                //将内容按顺序赋给对应的列对象
                Cell cell = row.createCell(j);
                cell.setCellValue(values.get(i).get(j));
                cell.setCellStyle(cellStyle);
            }
        }
    }
}
