package com.mybatis.sp.plus.step;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.Meta;
import com.mybatis.sp.plus.actions.*;
import com.mybatis.sp.plus.actions.Set;
import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.functions.*;
import com.mybatis.sp.plus.meta.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/15 9:10
 */
public class DmStepGenerator extends StepGenerator{

    public DmStepGenerator(List<Action> actions) {
        super(actions,"\"");
    }

    public void LimitToStep(Limit limit) {
        //向前寻找第一个不在括号中的SELECT，为其添加ROWNUM
        ListIterator<Step> stepIter = steps.listIterator(steps.size());
        //用来判断当前遇到的括号，只有在两者相等的情况下的select才是有效的
        int closeParen = 0;
        int openParen = 0;
        LinkedList<Step> tableStep = new LinkedList<>();
        while (stepIter.hasPrevious()) {
            Step step = stepIter.next();
            if (step.getStepName().equals("SELECT") && openParen == closeParen) {
                tableStep.addFirst(step);
                stepIter.remove();
                break;
            } else {
                if (step.getStepName() != null && step.getStepName().endsWith(")")) {
                    closeParen++;
                } else if (step.getStepName() != null && step.getStepName().endsWith("(")) {
                    openParen++;
                }
                tableStep.addFirst(step);
                stepIter.remove();
            }
        }
        if (limit.getOffset() > 0) {
            steps.addAll(createPageTable(tableStep, limit.getLimit(), limit.getOffset()));
        } else {
            steps.addAll(createLimitTable(tableStep, limit.getLimit()));
        }
    }

    List<Step> createPageTable(LinkedList<Step> tableStep, int limit, int offset) {
        List<Step> pageTableSteps = new ArrayList<>();
        pageTableSteps.add(new Step("SELECT"));
        pageTableSteps.add(new Step("*"));
        pageTableSteps.add(new Step("FROM"));
        pageTableSteps.add(new Step("("));
        pageTableSteps.add(new Step("SELECT"));
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("."));
        pageTableSteps.add(new Step("*"));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step("AS"));
        pageTableSteps.add(new Step(dialect + "RN_TMP" + dialect));
        pageTableSteps.add(new Step("FROM"));
        pageTableSteps.add(new Step("("));
        //将要分页的数据加入此处
        pageTableSteps.addAll(tableStep);
        pageTableSteps.add(new Step(")"));
        pageTableSteps.add(new Step("AS"));
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step("<="));
        pageTableSteps.add(new Step().setStepValue(offset + limit));
        pageTableSteps.add(new Step(")"));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step(dialect + "RN_TMP" + dialect));
        pageTableSteps.add(new Step(">="));
        pageTableSteps.add(new Step().setStepValue(offset));
        return pageTableSteps;
    }

    List<Step> createLimitTable(LinkedList<Step> tableStep, int limit) {
        List<Step> pageTableSteps = new ArrayList<>();
        pageTableSteps.add(new Step("SELECT"));
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("."));
        pageTableSteps.add(new Step("*"));
        pageTableSteps.add(new Step("FROM"));
        pageTableSteps.add(new Step("("));
        //将要分页的数据加入此处
        pageTableSteps.addAll(tableStep);
        pageTableSteps.add(new Step(")"));
        pageTableSteps.add(new Step("AS"));
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step("<="));
        pageTableSteps.add(new Step().setStepValue(limit));
        return pageTableSteps;
    }

    public void ConcatToStep(Concat concat) throws Exception {
        for (Field obj : concat.getObjs()) {
            fieldToStep(obj);
            steps.add(new Step("||"));
        }
        steps.removeLast();
    }

    public void InstrToStep(Instr instr) throws Exception {
        steps.add(new Step("INSTR("));
        fieldToStep(instr.getField());
        steps.add(new Step(","));
        ValueToStep(instr.getTarget());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(instr.getStart()));
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(instr.getTimes()));
        steps.add(new Step(")"));
    }

}
