package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.annotation._On;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Alias;
import com.github.mybatis.sp.plus.meta.Table;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 20:26
 */
@_On
public abstract class Join extends Action {
    Table table;

    public Join(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }


    public Join as(Alias alias) {
        table.setAlias(alias);
        return this;
    }

    public Join as(String alias) {
        table.setAlias(new Alias(alias));
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action Join");
        }
    }

}
