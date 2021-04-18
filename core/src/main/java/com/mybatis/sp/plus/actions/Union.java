package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.annotation._Select;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.ArrayList;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;

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
