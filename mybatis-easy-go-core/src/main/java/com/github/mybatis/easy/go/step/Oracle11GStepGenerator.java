package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.functions.GroupConcat;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
@DatabaseVersion(minVersion = "Oracle Database 11g Enterprise Edition Release 11.2")
public class Oracle11GStepGenerator extends OracleLikeStepGenerator {

    public Oracle11GStepGenerator(List<Action> actions) {
        super(actions);
    }

    @Override
    public void GroupConcatToStep(GroupConcat groupConcat) throws Exception {
        steps.add(new Step("LISTAGG("));
        Field field = groupConcat.getObjs().get(0);
        fieldToStep(field);
        if (groupConcat.getSeparator() != null) {
            steps.add(new Step(","));
            steps.add(new Step().setStepValue(groupConcat.getSeparator()));
        }
        steps.add(new Step(")"));
        steps.add(new Step("WITHIN GROUP("));
        steps.add(new Step("ORDER BY"));
        if (groupConcat.getOrders().size() > 0) {
            for (Order order : groupConcat.getOrders()) {
                orderToStep(order);
                steps.add(new Step(","));
            }
            steps.removeLast();
        } else {
            fieldToStep(field);
        }
        steps.add(new Step(")"));
    }
}
