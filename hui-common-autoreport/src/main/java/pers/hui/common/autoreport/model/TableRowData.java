package pers.hui.common.autoreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableRowData {

    //每一行数据
    private List<String> dataList;

}
