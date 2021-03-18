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
public class CaseWhenBinding extends ValBinding{
    private List<CaseWhenVal> caseWhenValList;
    /**
     * else 'TEST_END' end
     */
    private String elseVal;


    @Data
    public static class CaseWhenVal{
        /**
         * case when col1 > 10 and col < 20
         */
        private List<String> whenValList;

        /**
         * then 'TEST'
         * then 'case when '
         */
        private String thenVal;

        private List<CaseWhenBinding> childCaseWhenBindingList ;
    }
}
