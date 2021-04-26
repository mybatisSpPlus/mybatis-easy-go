package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.annotation.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:28
 */
@_Set
@_CrossJoin
@_LeftJoin
@_RightJoin
@_InnerJoin
@_FullJoin
public class Update extends Action {

    Table table;

    public Update(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action Update");
        }
    }
}
