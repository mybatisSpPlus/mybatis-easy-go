package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.annotation.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Order;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;
import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:58
 */
@_Set
@_Where
@_Union
@_UnionAll
@_LeftJoin
@_RightJoin
@_InnerJoin
@_FullJoin
@_GroupBy
@_OrderBy
@_Limit
public class On extends Action {

    List<Condition> conditions=new ArrayList<>();

    public On() {
    }

    public On(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public On(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public On setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public On addConditions(Condition... conditions) {
        if (this.conditions==null) {
            this.conditions = Arrays.asList(conditions);
        }else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (conditions.size()==0){
            throw new SelfCheckException("conditions can not be empty in action On");
        }
    }
}
