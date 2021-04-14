package com.mybatis.sp.plus;

import com.mybatis.sp.plus.actions.Having;
import com.mybatis.sp.plus.actions.On;
import com.mybatis.sp.plus.actions.Where;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.Result;
import com.mybatis.sp.plus.meta.Table;
import com.mybatis.sp.plus.spring.BaseMapper;
import com.mybatis.sp.plus.spring.BeanHelper;

import java.util.List;
import java.util.Map;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:41
 */
public abstract class Action {

    QueryBuilders builders;

    public QueryBuilders getBuilders() {
        return builders;
    }

    public Action setBuilders(QueryBuilders builders) {
        this.builders = builders;
        return this;
    }

    public Table asTable(Alias alias){
        return new Table().setAlias(alias).setActions(builders.getActionTree());
    }

    public Table asTable(String alias){
        return new Table().setAlias(new Alias(alias)).setActions(builders.getActionTree());
    }

    public void execute(){

    }

    public int executeUpdate(){
        return 0;
    }

    public int executeInsert(){
        return 0;
    }

    public int executeDelete(){
        return 0;
    }


    public Result executeSelect() throws Exception {
        List<Action> actions= QueryBuilderHelper.formatActionChain(builders.getActionTree());
        for (Action action : actions) {
            if (action instanceof Where){
                ((Where) action).setConditions(QueryBuilderHelper.formatConditionChain(((Where) action).getConditions()));
            }else if (action instanceof On){
                ((On) action).setConditions(QueryBuilderHelper.formatConditionChain(((On) action).getConditions()));
            }else if (action instanceof Having){
                ((Having) action).setConditions(QueryBuilderHelper.formatConditionChain(((Having) action).getConditions()));
            }
        }
        List<Map<String,Object>> map=getMapper().executeQuery(actions);
        return new Result(map);
    }


    public BaseMapper getMapper(){
        return BeanHelper.getBean(BaseMapper.class);
    }

    public abstract void selfCheck() throws SelfCheckException;
}

