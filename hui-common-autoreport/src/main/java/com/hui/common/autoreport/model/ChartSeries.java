package com.hui.common.autoreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartSeries {

    //系列名字
    private String seriesName;

    //该系列图表类别+值
    private List<ChartCategory> chartCategoryList;

}
