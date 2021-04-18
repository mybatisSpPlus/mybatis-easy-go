package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.annotation._Select;
import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:50
 */
@_Select
public class Union extends Action {

    public Union() {
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
