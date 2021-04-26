package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.annotation.*;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Alias;
import com.github.mybatis.sp.plus.meta.Table;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 14:58
 */
@_Where
@_Union
@_UnionAll
@_CrossJoin
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
