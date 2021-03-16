package pers.hui.calcite;

import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptRuleOperand;

/**
 * <code>TestRule</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/2 18:29.
 *
 * @author Gary.Hu
 */
public class TestRule extends RelOptRule {
    public TestRule(RelOptRuleOperand operand, String description) {

        super(operand, description);
    }

    @Override
    public void onMatch(RelOptRuleCall call) {

    }
}
