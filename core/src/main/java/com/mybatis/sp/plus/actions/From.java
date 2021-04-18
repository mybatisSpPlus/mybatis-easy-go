package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.annotation.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Order;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;
import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 14:58
 */
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
public class From extends Action {

    Table table;

    public From(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public From as(Alias alias) {
        table.setAlias(alias);
        return this;
    }

    public From as(String alias) {
        table.setAlias(new Alias(alias));
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action From");
        }
    }


}
