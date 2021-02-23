package pers.hui.common.autoreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ExcelData</code></b>
 * <p/>
 * Description:
 * <p/>
 * <b>Creation Time:</b> 2018/11/27 11:14.
 *
 * @author Hu weihui
 */
@Data
@NoArgsConstructor
public class ExcelData implements Serializable {
    private static final long serialVersionUID = 5298452161158057081L;

    private List<String> titles;

    private List<List<String>> values;

    public ExcelData(List<String> titles) {
        this.titles = titles;
    }

    public ExcelData(List<String> titles, List<List<String>> values) {
        this.titles = titles;
        this.values = values;
    }

}
