package pers.hui.common.beetl.model;

import lombok.Data;

import java.util.List;

/**
 * <code>CaseWhenBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/18 11:01.
 *
 * @author Gary.Hu
 */
@Data
public class CaseWhenBinding extends SqlBinding {
    /**
     * DEMO:
     * CASE
     *       WHEN LP_FLAG > 0 AND CP_FLAG > 0 THEN
     *         CASE WHEN CP_SALES_EXCL_VAT >= LP_SALES_EXCL_VAT THEN 'BRAND INCREASE' ELSE 'BRAND DECREASE' END
     *       WHEN LP_FLAG > 0 THEN
     *         CASE
     *           WHEN CP_GRP_FLAG > 0 THEN 'BRAND LASPED'
     *           WHEN CP_MEM_FLAG > 0 THEN 'CATE LASPED'
     *           ELSE 'WTCCN LAPSED'
     *         END
     *       WHEN CP_FLAG > 0 THEN
     *         CASE
     *           WHEN LP_GRP_FLAG > 0 THEN 'BRAND NEW'
     *           WHEN LP_MEM_FLAG > 0 THEN 'CATE NEW'
     *           ELSE 'WTCCN NEW'
     *         END
     *       ELSE 'NA'
     *     END
     */


    private List<WhenVal> whenValList;
    /**
     * else 'TEST_END' end
     */
    private String elseVal;
}
