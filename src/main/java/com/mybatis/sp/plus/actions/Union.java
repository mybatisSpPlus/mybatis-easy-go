package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.ArrayList;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:50
 */
public class Union extends Action {

    public Union() {
    }

    public Select select(Field... fields) {
        Select select=new Select(fields);
        getBuilders().getActionTree().add(select);
        select.setBuilders(getBuilders());
        return select;
    }

    public Select select(String... fieldNames) {
        List<Field> fields=new ArrayList<>();
        for (String fieldName : fieldNames) {
            fields.add(fieldNameToField(fieldName));
        }
        Select select=new Select(fields);
        getBuilders().getActionTree().add(select);
        select.setBuilders(getBuilders());
        return select;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
