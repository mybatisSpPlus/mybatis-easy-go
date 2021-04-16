package com.mybatis.sp.plus;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:41
 */
public abstract class Action {

    public static HashMap<String,Class> dbTypeToStepGenerator=new HashMap<>();

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
        List<Step> steps=getStepGenerator().toStep();
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
        return getMapper().executeUpdate(getStepGenerator().toStep());
    }

    public int executeInsert() throws Exception {
        return getMapper().executeInsert(getStepGenerator().toStep());
    }

    public int executeDelete() throws Exception {
        return getMapper().executeDelete(getStepGenerator().toStep());
    }

    public Result executeSelect() throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return new Result(map);
    }

    public <T> T executeOneSelect(Class<T> tClass) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToOne(tClass);
    }

    public <T> T executeOneSelect(Class<T> tClass, BiFunction<Class<T>,Map<String,Object>,T> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToOne(tClass,function);
    }

    public <T> T executeOneSelect(String typeName, BiFunction<String,Map<String,Object>,T> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToOne(typeName,function);
    }

    public <T> List<T> executeListSelect(Class<T> tClass) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToList(tClass);
    }

    public <T> List<T> executeListSelect(Class<T> tClass, BiFunction<Class<T>,List<Map<String,Object>>,List<T>> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToList(tClass,function);
    }

    public <T> List<T> executeListSelect(String typeName, BiFunction<String,List<Map<String,Object>>,List<T>> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToList(typeName,function);
    }

    public <T> List<T> executeListSelect(Function<List<Map<String,Object>>,List<T>> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToList(function);
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Class<T> tClass) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToPage(tClass);
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Class<T> tClass, BiFunction<Class<T>,List<Map<String,Object>>,Page<T>> function) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToPage(tClass,function);
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, String typeName, BiFunction<String,List<Map<String,Object>>,Page<T>> function) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToPage(typeName,function);
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Function<List<Map<String,Object>>,Page<T>> function) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep());
        return  new Result(map).convertToPage(function);
    }


    public BaseMapper getMapper(){
        return BeanHelper.getBean(BaseMapper.class);
    }

    public StepGenerator getStepGenerator() throws Exception {
        SqlSessionTemplate sessionTemplate=BeanHelper.getBean(SqlSessionTemplate.class);
        StepGenerator stepGenerator;
        if (dbTypeToStepGenerator.containsKey(sessionTemplate.getConfiguration().getDatabaseId())){
            stepGenerator= (StepGenerator) dbTypeToStepGenerator.get(sessionTemplate.getConfiguration().getDatabaseId()).getConstructor(List.class).newInstance(builders.getActionTree());
        }else {
            stepGenerator=new StepGenerator(builders.getActionTree(),"");
        }
        return stepGenerator;
    }

    public abstract void selfCheck() throws SelfCheckException;

}

