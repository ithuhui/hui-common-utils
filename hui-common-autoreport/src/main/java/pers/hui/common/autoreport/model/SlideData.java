package pers.hui.common.autoreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideData {
    //页码
    private Integer slidePage;
    //表格数据 支持多个表格 按照创建顺序渲染
    private List<TableData> tableDataList;
    //图表数据 支持多个图表 按照创建顺序渲染
    private List<ChartData> chartDataList;
    //文本数据 格式${}
    private Map<String, String> textMap;

}
