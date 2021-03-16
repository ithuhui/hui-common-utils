package pers.hui.calcite;

import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.type.RelDataType;

import java.util.List;

/**
 * <code>CommonViewExpanderImpl</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/2 17:20.
 *
 * @author Gary.Hu
 */
public class CommonViewExpanderImpl implements RelOptTable.ViewExpander{

    public CommonViewExpanderImpl() {
    }
    @Override
    public RelRoot expandView(RelDataType rowType, String queryString, List<String> schemaPath, List<String> viewPath) {
        return null;
    }
}
