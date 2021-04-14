package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:29
 */
public class Delete extends Action {

    List<Table> tables=new ArrayList<>();

    @Override
    public void selfCheck() throws SelfCheckException {
    }
}
