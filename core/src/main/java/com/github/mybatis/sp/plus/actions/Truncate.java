package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Table;

/**
 * @author zhouyu74748585@hotmail.com
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
