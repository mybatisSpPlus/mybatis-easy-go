package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.annotation._Select;
import com.github.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
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
