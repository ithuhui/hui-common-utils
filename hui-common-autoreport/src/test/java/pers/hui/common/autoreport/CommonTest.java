package pers.hui.common.autoreport;

import pers.hui.common.autoreport.core.ExcelGenerator;
import pers.hui.common.autoreport.model.ExcelData;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <code>CommonTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/25 0:31.
 *
 * @author Gary.Hu
 */

public class CommonTest {

    @Test
    public void excelTest() throws Exception {
        List<String> titles = Arrays.asList("title1", "title2", "title3");
        List<String> val1 = Arrays.asList("1-1", "1-2", "1-3");
        List<String> val2 = Arrays.asList("2-1", "2-2", "2-3");
        List<List<String>> values = Arrays.asList(val1, val2);
        ExcelData excelData = new ExcelData();
        excelData.setTitles(titles);
        excelData.setValues(values);
        ExcelGenerator.INSTANCE.gen("testExcel", "D:\\test", "sheet1", excelData, "xls");
    }
}
