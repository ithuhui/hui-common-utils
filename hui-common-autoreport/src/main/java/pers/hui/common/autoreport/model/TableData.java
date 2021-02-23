package pers.hui.common.autoreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableData {

    //表格一行数据
    private List<TableRowData> tableRowDataList;

}
