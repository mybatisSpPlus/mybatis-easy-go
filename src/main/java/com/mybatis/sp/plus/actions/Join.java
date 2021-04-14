package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 20:26
 */
public class Join extends Action {
    Table table;

    public Join(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public On on(Condition... conditions){
        On on=new On();
        return on;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            table.selfCheck();
        }else {
            throw new SelfCheckException("table can not be null in action Join");
        }
    }
}
