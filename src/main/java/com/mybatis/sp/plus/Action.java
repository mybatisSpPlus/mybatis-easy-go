package com.mybatis.sp.plus;

import com.mybatis.sp.plus.actions.Delete;
import com.mybatis.sp.plus.actions.InsertInto;
import com.mybatis.sp.plus.actions.Select;
import com.mybatis.sp.plus.actions.Update;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.Result;
import com.mybatis.sp.plus.meta.Table;
import com.mybatis.sp.plus.spring.BaseMapper;
import com.mybatis.sp.plus.spring.BeanHelper;
import com.mybatis.sp.plus.step.*;
import org.mybatis.spring.SqlSessionTemplate;

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
    public Table asTable(){
        return new Table().setActions(builders.getActionTree());
    }

    public Table asTable(Alias alias){
        return new Table().setAlias(alias).setActions(builders.getActionTree());
    }

    public Table asTable(String alias){
        return new Table().setAlias(new Alias(alias)).setActions(builders.getActionTree());
    }

    public void execute() throws Exception {
        List<Action> actions=builders.getActionTree();
        List<Step> steps=getStepGenerator(builders.getActionTree()).toStep();
        if(actions.size()>0){
            Action start=actions.get(0);
            if (start instanceof Select){
                executeSelect();
            }else if (start instanceof Delete){
                executeDelete();
            }else if (start instanceof Update){
                executeUpdate();
            }else if (start instanceof InsertInto){
                executeInsert();
            }else {
                getMapper().execute(steps);
            }
        }
    }

    public int executeUpdate() throws Exception {
        return getMapper().executeUpdate(getStepGenerator(builders.getActionTree()).toStep());
    }

    public int executeInsert() throws Exception {
        return getMapper().executeInsert(getStepGenerator(builders.getActionTree()).toStep());
    }

    public int executeDelete() throws Exception {
        return getMapper().executeDelete(getStepGenerator(builders.getActionTree()).toStep());
    }

    public Result executeSelect() throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator(builders.getActionTree()).toStep());
        return new Result(map);
    }


    public BaseMapper getMapper(){
        return BeanHelper.getBean(BaseMapper.class);
    }

    public StepGenerator getStepGenerator(List<Action> actions){
        SqlSessionTemplate sessionTemplate=BeanHelper.getBean(SqlSessionTemplate.class);
        StepGenerator stepGenerator;
        switch (sessionTemplate.getConfiguration().getDatabaseId()){
            case "mysql":
                stepGenerator= new MysqlStepGenerator(actions);
                break;
            case "oracle":
                stepGenerator= new OracleStepGenerator(actions);
                break;
            case "dm":
                stepGenerator= new DmStepGenerator(actions);
                break;
            case "postgresql":
                stepGenerator=new PgStepGenerator(actions);
                break;
            default:
                stepGenerator=new StepGenerator(actions,"");
        }
        return stepGenerator;
    }


    public abstract void selfCheck() throws SelfCheckException;

}
