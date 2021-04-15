package com.mybatis.sp.plus;

import com.mybatis.sp.plus.step.OracleStepGenerator;

import java.util.List;

import static com.mybatis.sp.plus.ActionMethods.select;
import static com.mybatis.sp.plus.ConditionMethods.like;
import static com.mybatis.sp.plus.FunctionMethods.max;
import static com.mybatis.sp.plus.MetaMethods.field;
import static com.mybatis.sp.plus.MetaMethods.starField;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/15 18:52
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        List<Action> actionList=select(max(field("guid"))).from(
                select(starField()).from("LABEL").where(like("labelName","标签")).asTable("T1")
        ).where(field("labelName").eq(
                select("labelName").from("LABEL_copy1").where(field("guid").lt(3691048027524630905L).and(field("guid").gt(1234L))).limit(1,2).asTable())
        ).groupBy("labelName").limit(1).getBuilders().getActionTree();
        System.out.println(new OracleStepGenerator(actionList).toSql());
    }
}
