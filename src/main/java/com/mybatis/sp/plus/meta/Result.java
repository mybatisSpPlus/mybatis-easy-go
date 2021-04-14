package com.mybatis.sp.plus.meta;

import com.github.pagehelper.Page;
import com.mybatis.sp.plus.QueryBuilderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 14:53
 */
public class Result {

    List<Map<String,Object>> resultList;

    public Result(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public <T> T convertToOne(Class<T> tClass) throws Exception {
        if (resultList.size()>0){
            return QueryBuilderHelper.convert(tClass,resultList.get(0));
        }
        return null;
    }
    public <T> T convertToOne(Class<T> tClass, BiFunction<Class<T>,Map<String,Object>,T> function) {
        if (resultList.size()>0) {
            return function.apply(tClass, resultList.get(0));
        }else {
            return null;
        }
    }
    public <T> T convertToOne(String typeName, BiFunction<String,Map<String,Object>,T> function) {
        if (resultList.size()>0) {
            return function.apply(typeName, resultList.get(0));
        }else {
            return null;
        }
    }
    public <T> T convertToOne(Function<Map<String,Object>,T> function){
        if (resultList.size()>0) {
            return function.apply(resultList.get(0));
        }else {
            return null;
        }
    }

    public <T> List<T> convertToList(Class<T> tClass) throws Exception {
        if (resultList.size()>0){
            return QueryBuilderHelper.convert(tClass,resultList);
        }
        return new ArrayList<>();
    }
    public <T> List<T> convertToList(Class<T> tClass, BiFunction<Class<T>,List<Map<String,Object>>,List<T>> function) {
        return function.apply(tClass,resultList);
    }
    public <T> List<T> convertToList(String typeName, BiFunction<String,List<Map<String,Object>>,List<T>> function) {
        return function.apply(typeName,resultList);
    }
    public <T> List<T> convertToList(Function<List<Map<String,Object>>,List<T>> function) {
        return function.apply(resultList);
    }

    public <T> Page<T> convertToPage(Class<T> tClass) throws Exception {
        if (resultList.size()>0){
            List<T> result= QueryBuilderHelper.convert(tClass,resultList);
            if (resultList instanceof Page){
                Page<T> page=new Page<T>().setPages(((Page<Map<String, Object>>) resultList).getPages())
                        .setPageNum(((Page<Map<String, Object>>) resultList).getPageNum())
                        .setPageSize(((Page<Map<String, Object>>) resultList).getPageSize());
                page.addAll(result);
                page.setTotal(((Page<Map<String, Object>>) resultList).getTotal());
                return (Page<T>) resultList;
            }
        }
        return new Page<>(0,0);
    }
    public <T> Page<T> convertToPage(Class<T> tClass, BiFunction<Class<T>,List<Map<String,Object>>,Page<T>> function) {
        return function.apply(tClass,resultList);
    }
    public <T> Page<T> convertToPage(String typeName, BiFunction<String,List<Map<String,Object>>,Page<T>> function) {
        return function.apply(typeName,resultList);
    }
    public <T> Page<T> convertToPage(Function<List<Map<String,Object>>,Page<T>> function) {
        return function.apply(resultList);
    }

}
