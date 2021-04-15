package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
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
public class Truncate extends Action {

    Table table;

    public Truncate(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action Truncate");
        }
    }


}
