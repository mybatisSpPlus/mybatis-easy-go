package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.annotation._From;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:29
 */
@_From
public class Delete extends Action {

    List<Table> tables=new ArrayList<>();

    public Delete() {
    }

    public Delete(Table... tables) {
        this.tables.addAll(Arrays.asList(tables));
    }

    public Delete(List<Table> tables) {
        this.tables.addAll(tables);
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
    }


}
