package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.actions.Limit;
import com.github.mybatis.easy.go.conditions.Regx;
import com.github.mybatis.easy.go.functions.*;
import com.github.mybatis.easy.go.meta.Alias;
import com.github.mybatis.easy.go.meta.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 给语法与oracle相似的数据库的通用的生成器
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 19:42
 */
public class OracleLikeStepGenerator extends StepGenerator {

    public OracleLikeStepGenerator(List<Action> actions) {
        super(actions, "\"");
    }

    public void LimitToStep(Limit limit) {
        //向前寻找第一个不在括号中的SELECT，为其添加ROWNUM
        ListIterator<Step> stepIter = steps.listIterator(steps.size());
        //用来判断当前遇到的括号，只有在两者相等的情况下的select才是有效的
        int closeParen = 0;
        int openParen = 0;
        LinkedList<Step> tableStep = new LinkedList<>();
        while (stepIter.hasPrevious()) {
            Step step = stepIter.previous();
            if ("SELECT".equals(step.getStepName()) && openParen == closeParen) {
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
        pageTableSteps.add(new Step(","));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step(dialect + "RN_TMP" + dialect));
        pageTableSteps.add(new Step("FROM"));
        pageTableSteps.add(new Step("("));
        //将要分页的数据加入此处
        pageTableSteps.addAll(tableStep);
        pageTableSteps.add(new Step(")"));
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step("<="));
        pageTableSteps.add(new Step().setStepValue(offset + limit));
        pageTableSteps.add(new Step(")"));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step(dialect + "RN_TMP" + dialect));
        pageTableSteps.add(new Step(">"));
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
        pageTableSteps.add(new Step(dialect + "PAGE_TMP_TABLE" + dialect));
        pageTableSteps.add(new Step("WHERE"));
        pageTableSteps.add(new Step("ROWNUM"));
        pageTableSteps.add(new Step("<="));
        pageTableSteps.add(new Step().setStepValue(limit));
        return pageTableSteps;
    }

    public void IfNullToStep(IfNull ifNull) throws Exception {
        steps.add(new Step("NVL("));
        fieldToStep(ifNull.getField());
        steps.add(new Step(","));
        valueToStep(ifNull.getDefaultValue());
        steps.add(new Step(")"));
    }

    public void NowToStep() throws Exception {
        steps.add(new Step("SYSDATE"));
    }

    public void LenToStep(Len len) throws Exception {
        steps.add(new Step("LENGTH("));
        fieldToStep(len.getField());
        steps.add(new Step(")"));
    }

    public void LenBToStep(LenB lenb) throws Exception {
        steps.add(new Step("LENGTHB("));
        fieldToStep(lenb.getField());
        steps.add(new Step(")"));
    }

    public void RegxToStep(Regx regx) throws Exception {
        steps.add(new Step("REGEXP_LIKE("));
        fieldToStep(regx.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(regx.getValue().toString()));
        steps.add(new Step(")"));
    }

    public void ConcatToStep(Concat concat) throws Exception {
        for (Field obj : concat.getObjs()) {
            fieldToStep(obj);
            steps.add(new Step("||"));
        }
        steps.removeLast();
    }

    public void GroupConcatToStep(GroupConcat groupConcat) throws Exception {
        steps.add(new Step("TO_CHAR("));
        steps.add(new Step("WM_CONCAT("));
        for (Field obj : groupConcat.getObjs()) {
            fieldToStep(obj);
            steps.add(new Step(","));
        }
        steps.removeLast();
        steps.add(new Step(")"));
        steps.add(new Step(")"));
    }

    public void ConvertToStep(Convert convert) throws Exception {
        steps.add(new Step("CONVERT("));
        fieldToStep(convert.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(convert.getTargetCharset()));
        if (StringUtils.isNotBlank(convert.getSourceCharset())) {
            steps.add(new Step(","));
            steps.add(new Step().setStepValue(convert.getSourceCharset()));
        }
        steps.add(new Step(")"));
    }

    public void InstrToStep(Instr instr) throws Exception {
        steps.add(new Step("INSTR("));
        fieldToStep(instr.getField());
        steps.add(new Step(","));
        valueToStep(instr.getTarget());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(instr.getStart()));
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(instr.getTimes()));
        steps.add(new Step(")"));
    }

    public void LeftToStep(Left left) throws Exception {
        super.SubstrToStep(new Substr(left.getField(), 0, left.getLength()));
    }

    public void RightToStep(Right right) throws Exception {
        super.SubstrToStep(new Substr(right.getField(), -right.getLength(), right.getLength()));
    }

    @Override
    public void aliasToStep(Alias alias) throws Exception {
        alias.selfCheck();
        steps.add(new Step(dialect + alias.getName() + dialect));
    }
}
