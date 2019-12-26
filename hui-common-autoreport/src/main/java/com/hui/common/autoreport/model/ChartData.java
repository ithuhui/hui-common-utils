package com.hui.common.autoreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    //所有系列
    private List<ChartSeries> chartSeriesList;

}
