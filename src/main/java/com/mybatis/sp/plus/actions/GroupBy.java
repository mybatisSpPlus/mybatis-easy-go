package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 16:59
 */
public class GroupBy extends Action {

    List<Field> fields=new ArrayList<>();

    public GroupBy() {
    }

    public GroupBy(List<Field> fields) {
        this.fields = fields;
    }

    public GroupBy(Field... fields) {
        this.fields = Arrays.asList(fields);
    }

    public List<Field> getFields() {
        return fields;
    }

    public GroupBy setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public GroupBy setFields(Field... fields) {
        if (this.fields==null) {
            this.fields = Arrays.asList(fields);
        }else {
            this.fields.addAll(Arrays.asList(fields));
        }
        return this;
    }


    public Having having(Condition... condition) {
        Having having=new Having(condition);
        getBuilders().getActionTree().add(having);
        having.setBuilders(getBuilders());
        return having;
    }

    public Union union(){
        Union union=new Union();
        getBuilders().getActionTree().add(union);
        union.setBuilders(getBuilders());
        return union;
    }

    public UnionAll unionAll(){
        UnionAll unionAll=new UnionAll();
        getBuilders().getActionTree().add(unionAll);
        unionAll.setBuilders(getBuilders());
        return unionAll;
    }
    public Limit limit(Limit limit) {
        getBuilders().getActionTree().add(limit);
        limit.setBuilders(getBuilders());
        return limit;
    }
    public Limit limit(int limit, int offset) {
        Limit limit1=new Limit(limit, offset);
        getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(getBuilders());
        return limit1;
    }
    public Limit limit(int limit) {
        Limit limit1=new Limit(limit);
        getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(getBuilders());
        return limit1;
    }
    @Override
    public void selfCheck() throws SelfCheckException {
        if (fields.size()==0){
            throw new SelfCheckException("fields can not be empty in action Group");
        }
    }

}
