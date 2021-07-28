package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.actions.*;
import com.github.mybatis.easy.go.conditions.*;
import com.github.mybatis.easy.go.functions.*;
import com.github.mybatis.easy.go.meta.*;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;
import com.github.mybatis.easy.go.supportAnnotation.UnSupportProperty;
import com.github.mybatis.easy.go.windowFunctions.*;
import com.github.mybatis.easy.go.windowFunctions.frame.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 基础的step生成器，是基于mysql的
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 8:59
 */
public class StepGenerator {
    Logger logger = LoggerFactory.getLogger(StepGenerator.class);
    String dialect = "";
    List<Action> actions;
    LinkedList<Step> steps = new LinkedList<>();


    public StepGenerator(List<Action> actions, String dialect) {
        this.actions = actions;
        this.dialect = dialect;
    }

    public void setNoDialect() {
        dialect = "";
    }



    public LinkedList<Step> toStep() throws Exception {
        steps.clear();
        for (Action action : actions) {
            actionToStep(action);
        }
        return steps;
    }

    public Object[] toMybatisSql(boolean printSql) throws Exception {
        long start = System.currentTimeMillis();
        if (steps.size() == 0) {
            toStep();
        }
        StringBuffer sb = new StringBuffer();
        HashMap<String, Object> params = new HashMap<>();
        int index = 0;
        for (Step step : steps) {
            if (StringUtils.isNotBlank(step.getStepName())) {
                sb.append(step.getStepName());
            } else {
                sb.append("#{params.p" + index + "}");
                params.put("p" + index, step.getStepValue());
                index++;
            }
            sb.append(" ");
        }
        long cost = System.currentTimeMillis() - start;
        logger.debug("sql generate cost:" + cost + " ms");
        if (printSql) {
            logger.debug("sql:" + sb);
        }
        return new Object[]{sb.toString(), params};
    }

    public String toSql() throws Exception {
        return toSql(false);
    }

    public String toSql(boolean setParameter) throws Exception {
        long start = System.currentTimeMillis();
        if (steps.size() == 0) {
            toStep();
        }
        StringBuffer sb = new StringBuffer();
        for (Step step : steps) {
            if (StringUtils.isNotBlank(step.getStepName())) {
                sb.append(step.getStepName());
            } else {
                if (setParameter) {
                    if (step.getStepValue() instanceof String) {
                        sb.append("'" + step.getStepValue() + "'");
                    } else {
                        sb.append(step.getStepValue());
                    }
                } else {
                    sb.append("?");
                }
            }
            sb.append(" ");
        }
        long cost = System.currentTimeMillis() - start;
        logger.info("sql generate cost :" + cost + " ms");
        return sb.toString();
    }


    public void InsertIntoToStep(InsertInto insertInto) throws Exception {
        steps.add(new Step("INSERT INTO"));
        tableToStep(insertInto.getTable());
        if (insertInto.getFields().size() > 0) {
            steps.add(new Step("("));
            for (Field field : insertInto.getFields()) {
                fieldToStep(field);
                steps.add(new Step(","));
            }
            steps.removeLast();
            steps.add(new Step(")"));
        }
        if (insertInto.getValues().size() > 0) {
            steps.add(new Step("VALUES"));
            for (List<Object> values : insertInto.getValues()) {
                steps.add(new Step("("));
                for (Object value : values) {
                    if (value instanceof Function) {
                        functionToStep((Function) value);
                    } else {
                        steps.add(new Step().setStepValue(value));
                    }
                    steps.add(new Step(","));
                }
                steps.removeLast();
                steps.add(new Step(")"));
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
    }

    public void UpdateToStep(Update update) throws Exception {
        steps.add(new Step("UPDATE"));
        tableToStep(update.getTable());
    }

    public void SelectToStep(Select select) throws Exception {
        steps.add(new Step("SELECT"));
        if (select.isDistinct()) {
            steps.add(new Step("DISTINCT"));
        }
        for (Field field : select.getFields()) {
            fieldToStep(field);
            steps.add(new Step(","));
        }
        steps.removeLast();
    }

    public void DeleteToStep(Delete delete) throws Exception {
        steps.add(new Step("DELETE"));
        if (delete.getTables().size() > 0) {
            for (Table table : delete.getTables()) {
                tableToStep(table);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
    }

    public void FromToStep(From from) throws Exception {
        steps.add(new Step("FROM"));
        tableToStep(from.getTable());
    }

    public void TruncateToStep(Truncate truncate) throws Exception {
        steps.add(new Step("TRUNCATE"));
        tableToStep(truncate.getTable());
    }

    public void CrossJoinToStep(CrossJoin crossJoin) throws Exception {
        steps.add(new Step("CROSS JOIN"));
        tableToStep(crossJoin.getTable());
    }

    public void FullJoinToStep(FullJoin fullJoin) throws Exception {
        steps.add(new Step("FULL JOIN"));
        tableToStep(fullJoin.getTable());
    }

    public void InnerJoinToStep(InnerJoin innerJoin) throws Exception {
        steps.add(new Step("INNER JOIN"));
        tableToStep(innerJoin.getTable());
    }

    public void LeftJoinToStep(LeftJoin leftJoin) throws Exception {
        steps.add(new Step("LEFT JOIN"));
        tableToStep(leftJoin.getTable());
    }

    public void RightJoinToStep(RightJoin rightJoin) throws Exception {
        steps.add(new Step("RIGHT JOIN"));
        tableToStep(rightJoin.getTable());
    }

    public void GroupByToStep(GroupBy groupBy) throws Exception {
        steps.add(new Step("GROUP BY"));
        for (Field field : groupBy.getFields()) {
            fieldToStep(field);
            steps.add(new Step(","));
        }
        steps.removeLast();
    }

    public void HavingToStep(Having having) throws Exception {
        if (having.getConditions().size() > 0) {
            steps.add(new Step("HAVING"));
            int stepLength = steps.size();
            for (Condition condition : having.getConditions()) {
                conditionToStep(condition);
                if (stepLength < steps.size()) {
                    steps.add(new Step("AND"));
                }
            }
            steps.removeLast();
        }
    }

    public void OrdersToStep(Orders orders) throws Exception {
        if (orders.getOrders().size() > 0) {
            steps.add(new Step("ORDER BY"));
            for (Order order : orders.getOrders()) {
                orderToStep(order);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
    }

    public void LimitToStep(Limit limit) {
        if (limit instanceof NoLimit) {
            return;
        }
        steps.add(new Step("LIMIT"));
        steps.add(new Step().setStepValue(limit.getLimit()));
        if (limit.getOffset() > 0) {
            steps.add(new Step("OFFSET"));
            steps.add(new Step().setStepValue(limit.getOffset()));
        }
    }

    public void OnToStep(On on) throws Exception {
        steps.add(new Step("ON"));
        int stepLength = steps.size();
        for (Condition condition : on.getConditions()) {
            conditionToStep(condition);
            if (stepLength < steps.size()) {
                steps.add(new Step("AND"));
            }
        }
        if (stepLength < steps.size()) {
            steps.removeLast();
        } else {
            throw new Exception("conditions can not be empty in action On");
        }
    }

    public void WhereToStep(Where where) throws Exception {
        if (where.getConditions().size() > 0) {
            steps.add(new Step("WHERE"));
            int stepLength = steps.size();
            for (Condition condition : where.getConditions()) {
                conditionToStep(condition);
                if (stepLength < steps.size()) {
                    steps.add(new Step("AND"));
                }
            }
            steps.removeLast();
        }
    }

    public void WindowToStep(Window window) throws Exception {
        steps.add(new Step("WINDOW "));
        steps.add(new Step(window.getWindowAlias()));
        steps.add(new Step("AS("));
        overInfoToStep(window.getPartitions(), window.getOrders(), window.isUseRanger(), window.isUseRows(), window.getStart(), window.getEnd());
    }

    public void SetToStep(Set set) throws Exception {
        steps.add(new Step("SET"));
        for (Map.Entry<Field, Object> entry : set.getFieldValue().entrySet()) {
            fieldToStep(entry.getKey());
            steps.add(new Step("="));
            if (entry.getValue() instanceof Function) {
                functionToStep((Function) entry.getValue());
            } else if (entry.getValue() instanceof Field) {
                fieldToStep((Field) entry.getValue());
            } else {
                steps.add(new Step().setStepValue(entry.getValue()));
            }
            steps.add(new Step(","));
        }
        steps.removeLast();
    }

    public void UnionToStep() {
        steps.add(new Step("UNION"));
    }

    public void UnionAllToStep() {
        steps.add(new Step("UNION ALL"));
    }

    public void SubActionBeginToStep() {
        steps.add(new Step("("));
    }

    public void SubActionEndToStep() {
        steps.add(new Step(")"));
    }

    public void actionToStep(Action action) throws Exception {
        action.selfCheck();
        checkUnSupport(action);
        String name = action.getClass().getSimpleName();
        switch (name) {
            case "CrossJoin":
                CrossJoinToStep((CrossJoin) action);
                break;
            case "Delete":
                DeleteToStep((Delete) action);
                break;
            case "From":
                FromToStep((From) action);
                break;
            case "FullJoin":
                FullJoinToStep((FullJoin) action);
                break;
            case "GroupBy":
                GroupByToStep((GroupBy) action);
                break;
            case "Having":
                HavingToStep((Having) action);
                break;
            case "InsertInto":
                InsertIntoToStep((InsertInto) action);
                break;
            case "InnerJoin":
                InnerJoinToStep((InnerJoin) action);
                break;
            case "LeftJoin":
                LeftJoinToStep((LeftJoin) action);
                break;
            case "Limit":
            case "NoLimit":
                LimitToStep((Limit) action);
                break;
            case "Orders":
                OrdersToStep((Orders) action);
                break;
            case "On":
                OnToStep((On) action);
                break;
            case "RightJoin":
                RightJoinToStep((RightJoin) action);
                break;
            case "Set":
                SetToStep((Set) action);
                break;
            case "SubActionBegin":
                SubActionBeginToStep();
                break;
            case "SubActionEnd":
                SubActionEndToStep();
                break;
            case "Select":
                SelectToStep((Select) action);
                break;
            case "Truncate":
                TruncateToStep((Truncate) action);
                break;
            case "Union":
                UnionToStep();
                break;
            case "UnionAll":
                UnionAllToStep();
                break;
            case "Update":
                UpdateToStep((Update) action);
                break;
            case "Where":
                WhereToStep((Where) action);
                break;
            case "Window":
                WindowToStep((Window) action);
                break;
            default:
                throw new Exception("action :" + name + " not supported");
        }
    }

    public void AndToStep(And and) throws Exception {
        if (and.getAndCondition().size() > 0) {
            steps.add(new Step("("));
            int stepLength = steps.size();
            for (Condition condition : and.getAndCondition()) {
                conditionToStep(condition);
                if (stepLength < steps.size()) {
                    steps.add(new Step("AND"));
                }
            }
            steps.removeLast();
            if (stepLength < steps.size()) {
                steps.add(new Step(")"));
            }
        }
    }

    public void OrToStep(Or or) throws Exception {
        if (or.getOrCondition().size() > 0) {
            steps.add(new Step("("));
            int stepLength = steps.size();
            for (Condition condition : or.getOrCondition()) {
                conditionToStep(condition);
                if (stepLength < steps.size()) {
                    steps.add(new Step("OR"));
                }
            }
            steps.removeLast();
            if (stepLength < steps.size()) {
                steps.add(new Step(")"));
            }
        }
    }

    public void BetweenToStep(Between between) throws Exception {
        fieldToStep(between.getField());
        steps.add(new Step("BETWEEN"));
        if (between.getStartValue() instanceof Function) {
            functionToStep((Function) between.getStartValue());
        } else {
            steps.add(new Step().setStepValue(between.getStartValue()));
        }
        steps.add(new Step("AND"));
        if (between.getEndValue() instanceof Function) {
            functionToStep((Function) between.getEndValue());
        } else {
            steps.add(new Step().setStepValue(between.getEndValue()));
        }
    }

    public void EqToStep(Eq eq) throws Exception {
        fieldToStep(eq.getField());
        steps.add(new Step("="));
        valueToStep(eq.getValue());
    }

    public void NeqToStep(Neq neq) throws Exception {
        fieldToStep(neq.getField());
        steps.add(new Step("!="));
        valueToStep(neq.getValue());
    }

    public void GtToStep(Gt gt) throws Exception {
        fieldToStep(gt.getField());
        steps.add(new Step(">"));
        valueToStep(gt.getValue());
    }

    public void GteToStep(Gte gte) throws Exception {
        fieldToStep(gte.getField());
        steps.add(new Step(">="));
        valueToStep(gte.getValue());
    }

    public void LtToStep(Lt lt) throws Exception {
        fieldToStep(lt.getField());
        steps.add(new Step("<"));
        valueToStep(lt.getValue());
    }

    public void LteToStep(Lte lte) throws Exception {
        fieldToStep(lte.getField());
        steps.add(new Step("<="));
        valueToStep(lte.getValue());
    }

    public void InToStep(In in) throws Exception {
        fieldToStep(in.getField());
        steps.add(new Step("IN"));
        if (in.getValues().size() > 0) {
            steps.add(new Step("("));
            for (Object value : in.getValues()) {
                valueToStep(value);
                steps.add(new Step(","));
            }
            steps.removeLast();
            steps.add(new Step(")"));
        } else {
            tableToStep(in.getTable());
        }
    }

    public void IsNotNullToStep(IsNotNull isNotNull) throws Exception {
        fieldToStep(isNotNull.getField());
        steps.add(new Step("IS NOT NULL"));
    }

    public void IsNullToStep(IsNull isNull) throws Exception {
        fieldToStep(isNull.getField());
        steps.add(new Step("IS NULL"));
    }

    public void LikeToStep(Like like) throws Exception {
        fieldToStep(like.getField());
        steps.add(new Step("LIKE"));
        steps.add(new Step().setStepValue("%" + like.getValue() + "%"));
    }

    public void StartWithToStep(StartWith startWith) throws Exception {
        fieldToStep(startWith.getField());
        steps.add(new Step("LIKE"));
        steps.add(new Step().setStepValue(startWith.getValue() + "%"));
    }

    public void EndWithToStep(EndWith endWith) throws Exception {
        fieldToStep(endWith.getField());
        steps.add(new Step("LIKE"));
        steps.add(new Step().setStepValue("%" + endWith.getValue()));
    }

    public void NotToStep(Not condition) throws Exception {
        steps.add(new Step("NOT"));
        conditionToStep(condition.getNotCondition());
    }

    public void RegxToStep(Regx regx) throws Exception {
        fieldToStep(regx.getField());
        steps.add(new Step("REGEXP"));
        steps.add(new Step("'" + regx.getValue().toString() + "'"));
    }

    public void conditionToStep(Condition condition) throws Exception {
        condition.selfCheck();
        checkUnSupport(condition);
        String name = condition.getClass().getSimpleName();
        switch (name) {
            case "And":
                AndToStep((And) condition);
                break;
            case "Between":
                BetweenToStep((Between) condition);
                break;
            case "Eq":
                EqToStep((Eq) condition);
                break;
            case "EndWith":
                EndWithToStep((EndWith) condition);
                break;
            case "Gt":
                GtToStep((Gt) condition);
                break;
            case "Gte":
                GteToStep((Gte) condition);
                break;
            case "In":
                InToStep((In) condition);
                break;
            case "IsNotNull":
                IsNotNullToStep((IsNotNull) condition);
                break;
            case "IsNull":
                IsNullToStep((IsNull) condition);
                break;
            case "Like":
                LikeToStep((Like) condition);
                break;
            case "Lt":
                LtToStep((Lt) condition);
                break;
            case "Lte":
                LteToStep((Lte) condition);
                break;
            case "Neq":
                NeqToStep((Neq) condition);
                break;
            case "Not":
                NotToStep((Not) condition);
                break;
            case "Or":
                OrToStep((Or) condition);
                break;
            case "Regx":
                RegxToStep((Regx) condition);
                break;
            case "StartWith":
                StartWithToStep((StartWith) condition);
                break;
            default:
                throw new Exception("condition :" + name + " not supported");
        }
    }

    public void AvgToStep(Avg avg) throws Exception {
        steps.add(new Step("AVG("));
        fieldToStep(avg.getField());
        steps.add(new Step(")"));
    }

    public void CaseToStep(Case caze) throws Exception {
        steps.add(new Step("CASE"));
        if (caze.getCaseCondition() != null) {
            valueToStep(caze.getCaseCondition());
        }
        for (int i = 0; i < caze.getWhen().size(); i++) {
            steps.add(new Step("WHEN"));
            valueToStep(caze.getWhen().get(i));
            steps.add(new Step("THEN"));
            valueToStep(caze.getThenValue().get(i));
        }
        steps.add(new Step("ELSE"));
        valueToStep(caze.getElseValue());
        steps.add(new Step("END"));
    }

    public void ConcatToStep(Concat concat) throws Exception {
        if (concat.getSeparator() == null) {
            steps.add(new Step("CONCAT("));
            for (Field obj : concat.getObjs()) {
                fieldToStep(obj);
                steps.add(new Step(","));
            }
        } else {
            steps.add(new Step("CONCAT_WS("));
            steps.add(new Step().setStepValue(concat.getSeparator()));
            steps.add(new Step(","));
            for (Field obj : concat.getObjs()) {
                fieldToStep(obj);
                steps.add(new Step(","));
            }
        }
        steps.removeLast();
        steps.add(new Step(")"));
    }

    public void GroupConcatToStep(GroupConcat groupConcat) throws Exception {
        steps.add(new Step("GROUP_CONCAT("));
        if (groupConcat.isDistinct()) {
            steps.add(new Step("DISTINCT"));
        }
        for (Field obj : groupConcat.getObjs()) {
            fieldToStep(obj);
            steps.add(new Step(","));
        }
        steps.removeLast();
        if (groupConcat.getOrders().size() > 0) {
            steps.add(new Step("ORDER BY"));
            for (Order order : groupConcat.getOrders()) {
                orderToStep(order);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
        if (groupConcat.getSeparator() != null) {
            steps.add(new Step("SEPARATOR"));
            steps.add(new Step().setStepValue(groupConcat.getSeparator()));
        }
        steps.add(new Step(")"));
    }

    public void ConvertToStep(Convert convert) throws Exception {
        steps.add(new Step("CONVERT("));
        fieldToStep(convert.getField());
        steps.add(new Step("USING"));
        steps.add(new Step().setStepValue(convert.getTargetCharset()));
        steps.add(new Step(")"));
    }

    public void CountToStep(Count count) throws Exception {
        steps.add(new Step("COUNT("));
        if (count.isDistinct()) {
            steps.add(new Step("DISTINCT"));
        }
        fieldToStep(count.getField());
        steps.add(new Step(")"));
    }

    public void FormatToStep(Format format) throws Exception {
        steps.add(new Step("FORMAT("));
        fieldToStep(format.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(format.getFormat()));
        steps.add(new Step(")"));
    }

    public void LcaseToStep(Lcase lcase) throws Exception {
        steps.add(new Step("LCASE("));
        fieldToStep(lcase.getField());
        steps.add(new Step(")"));
    }

    public void UcaseToStep(Ucase ucase) throws Exception {
        steps.add(new Step("UCASE("));
        fieldToStep(ucase.getField());
        steps.add(new Step(")"));
    }

    public void LenToStep(Len len) throws Exception {
        steps.add(new Step("CHAR_LENGTH("));
        fieldToStep(len.getField());
        steps.add(new Step(")"));
    }

    public void LenBToStep(LenB lenb) throws Exception {
        steps.add(new Step("LENGTH("));
        fieldToStep(lenb.getField());
        steps.add(new Step(")"));
    }

    public void LeftToStep(Left left) throws Exception {
        steps.add(new Step("LEFT("));
        fieldToStep(left.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(left.getLength()));
        steps.add(new Step(")"));
    }

    public void RightToStep(Right right) throws Exception {
        steps.add(new Step("RIGHT("));
        fieldToStep(right.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(right.getLength()));
        steps.add(new Step(")"));
    }

    public void MaxToStep(Max max) throws Exception {
        steps.add(new Step("MAX("));
        fieldToStep(max.getField());
        steps.add(new Step(")"));
    }

    public void MinToStep(Min min) throws Exception {
        steps.add(new Step("MIN("));
        fieldToStep(min.getField());
        steps.add(new Step(")"));
    }

    public void RoundToStep(Round round) throws Exception {
        steps.add(new Step("ROUND("));
        fieldToStep(round.getField());
        steps.add(new Step(")"));
    }

    public void SumToStep(Sum sum) throws Exception {
        steps.add(new Step("SUM("));
        fieldToStep(sum.getField());
        steps.add(new Step(")"));
    }

    public void NowToStep() throws Exception {
        steps.add(new Step("NOW()"));
    }

    public void SubstrToStep(Substr substr) throws Exception {
        steps.add(new Step("SUBSTR("));
        fieldToStep(substr.getField());
        steps.add(new Step(","));
        valueToStep(substr.getStart());
        steps.add(new Step(","));
        valueToStep(substr.getLength());
        steps.add(new Step(")"));
    }

    public void InstrToStep(Instr instr) throws Exception {
        steps.add(new Step("INSTR("));
        fieldToStep(instr.getField());
        steps.add(new Step(","));
        valueToStep(instr.getTarget());
        steps.add(new Step(")"));
    }

    public void IfNullToStep(IfNull ifNull) throws Exception {
        steps.add(new Step("IFNULL("));
        fieldToStep(ifNull.getField());
        steps.add(new Step(","));
        valueToStep(ifNull.getDefaultValue());
        steps.add(new Step(")"));
    }


    public void ReplaceToStep(Replace replace) throws Exception {
        steps.add(new Step("REPLACE("));
        fieldToStep(replace.getField());
        steps.add(new Step(","));
        valueToStep(replace.getOldStr());
        steps.add(new Step(","));
        valueToStep(replace.getNewStr());
        steps.add(new Step(")"));
    }

    public void AddToStep(Add add) throws Exception {
        valueToStep(add.getValueA());
        steps.add(new Step("+"));
        valueToStep(add.getValueB());
    }

    public void SubtractToStep(Subtract subtract) throws Exception {
        valueToStep(subtract.getValueA());
        steps.add(new Step("-"));
        valueToStep(subtract.getValueB());
    }

    public void MultiplyToStep(Multiply multiply) throws Exception {
        valueToStep(multiply.getValueA());
        steps.add(new Step("*"));
        valueToStep(multiply.getValueB());
    }

    public void DivideToStep(Divide divide) throws Exception {
        valueToStep(divide.getValueA());
        steps.add(new Step("/"));
        valueToStep(divide.getValueB());
    }

    public void ModToStep(Mod mod) throws Exception {
        valueToStep(mod.getValueA());
        steps.add(new Step("%"));
        valueToStep(mod.getValueB());
    }

    public void OverWindowToStep(OverWindow function) throws Exception {
        functionToStep(function.getWindowFunction());
        steps.add(new Step("OVER"));
        steps.add(new Step(function.getWindowAlias()));
    }

    public void OverToStep(Over function) throws Exception {
        functionToStep(function.getWindowFunction());
        steps.add(new Step("OVER("));
        overInfoToStep(function.getPartitions(), function.getOrders(), function.isUseRanger(), function.isUseRows(), function.getStart(), function.getEnd());
    }

    private void overInfoToStep(List<Field> partitions, List<Order> orders, boolean useRanger, boolean useRows, Frame start, Frame end) throws Exception {
        if (partitions !=null&& partitions.size()>0) {
            steps.add(new Step("PARTITION BY"));
            for (Field partition : partitions) {
                fieldToStep(partition);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
        if (orders !=null&& orders.size()>0){
            steps.add(new Step("ORDER BY"));
            for (Order order : orders) {
                orderToStep(order);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
        if (useRanger || useRows) {
            if (useRows) {
                steps.add(new Step("ROWS BETWEEN"));
            } else if (useRanger) {
                steps.add(new Step("RANGER BETWEEN"));
            }
            frameToStep(start);
            steps.add(new Step("AND"));
            frameToStep(end);
        }
        steps.add(new Step(")"));
    }

    public void RowNumberToStep() {
        steps.add(new Step("ROW_NUMBER()"));
    }

    public void RankToStep() {
        steps.add(new Step("RANK()"));
    }

    public void PercentRankToStep() {
        steps.add(new Step("PERCENT_RANK()"));
    }

    public void NthValueToStep(NthValue function) throws Exception {
        steps.add(new Step("NTH_VALUE("));
        fieldToStep(function.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(function.getIndex()));
        steps.add(new Step(")"));
    }

    public void NtileToStep(Ntile function) {
        steps.add(new Step("NTILE("));
        steps.add(new Step().setStepValue(function.getGroupCount()));
        steps.add(new Step(")"));
    }

    public void LeadToStep(Lead function) throws Exception {
        steps.add(new Step("LEAD("));
        fieldToStep(function.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(function.getRowCount()));
        steps.add(new Step(")"));
    }

    public void LastValueToStep(LastValue function) throws Exception {
        steps.add(new Step("LAST_VALUE("));
        fieldToStep(function.getField());
        steps.add(new Step(")"));
    }

    public void LagToStep(Lag function) throws Exception {
        steps.add(new Step("LAG("));
        fieldToStep(function.getField());
        steps.add(new Step(","));
        steps.add(new Step().setStepValue(function.getRowCount()));
        steps.add(new Step(")"));
    }

    public void FirstValueToStep(FirstValue function) throws Exception {
        steps.add(new Step("FIRST_VALUE("));
        fieldToStep(function.getField());
        steps.add(new Step(")"));
    }

    public void DenseRankToStep() {
        steps.add(new Step("DENSE_RANK()"));
    }

    public void CumeDistToStep() {
        steps.add(new Step("CUME_DIST()"));
    }

    public void CustomFunctionToStep(CustomFunction customFunction) throws Exception {
        steps.add(new Step(customFunction.getFunctionName() + "("));
        if (customFunction.getParameters().size() > 0) {
            for (Object parameter : customFunction.getParameters()) {
                valueToStep(parameter);
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
        steps.add(new Step(")"));
    }

    public void functionToStep(Function function) throws Exception {
        String name = function.getClass().getSimpleName();
        switch (name) {
            case "Add":
                AddToStep((Add) function);
                break;
            case "Avg":
                AvgToStep((Avg) function);
                break;
            case "Case":
                CaseToStep((Case) function);
                break;
            case "Concat":
                ConcatToStep((Concat) function);
                break;
            case "Convert":
                ConvertToStep((Convert) function);
                break;
            case "Count":
                CountToStep((Count) function);
                break;
            case "CumeDist":
                CumeDistToStep();
                break;
            case "CustomFunction":
                CustomFunctionToStep((CustomFunction) function);
                break;
            case "Divide":
                DivideToStep((Divide) function);
                break;
            case "DenseRank":
                DenseRankToStep();
                break;
            case "FirstValue":
                FirstValueToStep((FirstValue) function);
                break;
            case "Format":
                FormatToStep((Format) function);
                break;
            case "GroupConcat":
                GroupConcatToStep((GroupConcat) function);
                break;
            case "Instr":
                InstrToStep((Instr) function);
                break;
            case "IfNull":
                IfNullToStep((IfNull) function);
                break;
            case "Lag":
                LagToStep((Lag) function);
                break;
            case "LastValue":
                LastValueToStep((LastValue) function);
                break;
            case "Lcase":
                LcaseToStep((Lcase) function);
                break;
            case "Lead":
                LeadToStep((Lead) function);
                break;
            case "Len":
                LenToStep((Len) function);
                break;
            case "LenB":
                LenBToStep((LenB) function);
                break;
            case "Left":
                LeftToStep((Left) function);
                break;
            case "Max":
                MaxToStep((Max) function);
                break;
            case "Min":
                MinToStep((Min) function);
                break;
            case "Mod":
                ModToStep((Mod) function);
                break;
            case "Multiply":
                MultiplyToStep((Multiply) function);
                break;
            case "Now":
                NowToStep();
                break;
            case "Ntile":
                NtileToStep((Ntile)function);
                break;
            case "NthValue":
                NthValueToStep((NthValue)function);
                break;
            case "Over":
                OverToStep((Over)function);
                break;
            case "OverWindow":
                OverWindowToStep((OverWindow)function);
                break;
            case "PercentRank":
                PercentRankToStep();
                break;
            case "Rank":
                RankToStep();
                break;
            case "Right":
                RightToStep((Right) function);
                break;
            case "Replace":
                ReplaceToStep((Replace) function);
                break;
            case "Round":
                RoundToStep((Round) function);
                break;
            case "RowNumber":
                RowNumberToStep();
                break;
            case "Substr":
                SubstrToStep((Substr) function);
                break;
            case "Sum":
                SumToStep((Sum) function);
                break;
            case "Subtract":
                SubtractToStep((Subtract) function);
                break;
            case "Ucase":
                UcaseToStep((Ucase) function);
                break;
            default:
                throw new Exception("function :" + name + " not supported");
        }
    }



    public void valueToStep(Object value) throws Exception {
        if (value instanceof Field) {
            fieldToStep((Field) value);
        } else if (value instanceof Table) {
            tableToStep((Table) value);
        } else if (value instanceof NullValue) {
            steps.add(new Step("NULL"));
        } else {
            steps.add(new Step().setStepValue(value));
        }
    }

    public void fieldToStep(Field field) throws Exception {
        field.selfCheck();
        checkUnSupport(field);
        if (StringUtils.isNotBlank(field.getSpecialPrefix())) {
            steps.add(new Step(field.getSpecialPrefix()));
        }
        if (field instanceof ConstantField) {
            steps.add(new Step().setStepValue(((ConstantField) field).getConstant()));
        } else if (field instanceof AllField) {
            steps.add(new Step("*"));
        } else if (field instanceof Function) {
            functionToStep((Function) field);
        } else if (field instanceof Condition) {
            conditionToStep((Condition) field);
        } else {
            if (StringUtils.isNotBlank(field.getTableName())) {
                steps.add(new Step(dialect + field.getTableName() + dialect));
                steps.add(new Step("."));
            }
            if (field.getName().equals("*")) {
                steps.add(new Step("*"));
            } else {
                steps.add(new Step(dialect + field.getName() + dialect));
            }

        }
        if (StringUtils.isNotBlank(field.getSpecialPostfix())) {
            steps.add(new Step(field.getSpecialPostfix()));
        }
        if (field.getAlias() != null) {
            aliasToStep(field.getAlias());
        }
    }

    public void aliasToStep(Alias alias) throws Exception {
        alias.selfCheck();
        checkUnSupport(alias);
        steps.add(new Step("AS"));
        steps.add(new Step(dialect + alias.getName() + dialect));
    }

    public void tableToStep(Table table) throws Exception {
        table.selfCheck();
        checkUnSupport(table);
        if (StringUtils.isNotBlank(table.getSpecialPrefix())) {
            steps.add(new Step(table.getSpecialPrefix()));
        }
        if (StringUtils.isNotBlank(table.getName())) {
            if (StringUtils.isNotBlank(table.getSchema())) {
                steps.add(new Step(dialect + table.getSchema() + dialect));
                steps.add(new Step("."));
            }
            steps.add(new Step(dialect + table.getName() + dialect));
        } else {
            steps.add(new Step("("));
            for (Action action : table.getActions()) {
                actionToStep(action);
            }
            steps.add(new Step(")"));
        }
        if (StringUtils.isNotBlank(table.getSpecialPostfix())) {
            steps.add(new Step(table.getSpecialPostfix()));
        }
        if (table.getAlias() != null) {
            aliasToStep(table.getAlias());
        }
    }

    public void orderToStep(Order order) throws Exception {
        order.selfCheck();
        checkUnSupport(order);
        fieldToStep(order.getField());
        if (order.isDesc()) {
            steps.add(new Step("DESC"));
        }
    }

    public void frameToStep(Frame frame){
        if (frame instanceof CurrentRow){
            steps.add(new Step("CURRENT ROW"));
        }else if (frame instanceof Following){
            steps.add(new Step().setStepValue(((Following) frame).getRows()));
            steps.add(new Step("FOLLOWING"));
        }else if (frame instanceof Preceding){
            steps.add(new Step().setStepValue(((Preceding) frame).getRows()));
            steps.add(new Step("PRECEDING"));
        }else if (frame instanceof UnboundedFollowing){
            steps.add(new Step("UNBOUNDED FOLLOWING"));
        }else if (frame instanceof UnboundedPreceding){
            steps.add(new Step("UNBOUNDED PRECEDING"));
        }
    }

    private void checkUnSupport(Object obj) throws IllegalAccessException {
        Class generatorClass = this.getClass();
        checkUnSupport(generatorClass, obj.getClass(), obj);
    }

    private void checkUnSupport(Class generatorClass, Class objClazz, Object obj) throws IllegalAccessException {
        String objName = obj.getClass().getSimpleName();
        String dbName = generatorClass.getSimpleName().split("Step")[0];
        UnSupport unSupport= (UnSupport) objClazz.getAnnotation(UnSupport.class);
        if (unSupport!=null){
            for (Class aClass : unSupport.unSupportGenerator()) {
                if (aClass==generatorClass){
                    throw new UnsupportedOperationException(objClazz.getName() + " is unsupported in " + objName + " with " + dbName + ". Please check the Query");
                }
            }
        }
        for (java.lang.reflect.Field declaredField : objClazz.getDeclaredFields()) {
            UnSupportProperty usp = declaredField.getAnnotation(UnSupportProperty.class);
            if (usp == null) {
                continue;
            }
            for (Class aClass : usp.unSupportGenerator()) {
                if (aClass == generatorClass) {
                    declaredField.setAccessible(true);
                    Object objValue = declaredField.get(obj);
                    if (objValue != null) {
                        if (objValue instanceof List) {
                            if (((List<?>) objValue).size() > 0) {
                                logger.error("Property[" + declaredField.getName() + "] is unsupported in " + objName + " with " + dbName + ". Please check the Query");
                            }
                        } else if (objValue.getClass() == boolean.class) {
                            if ((boolean) objValue) {
                                logger.error("Property[" + declaredField.getName() + "] is unsupported in " + objName + " with " + dbName + ". Please check the Query");
                            }
                        } else {
                            logger.error("Property[" + declaredField.getName() + "] is unsupported in " + objName + " with " + dbName + ". Please check the Query");
                        }
                    }
                }
            }
        }
        if (objClazz.getSuperclass() != Object.class) {
            checkUnSupport(generatorClass, objClazz.getSuperclass(), obj);
        }
    }
}
