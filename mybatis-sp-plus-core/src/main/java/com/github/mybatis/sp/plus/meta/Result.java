package com.github.mybatis.sp.plus.meta;

import com.github.mybatis.sp.plus.PageRecord;
import com.github.mybatis.sp.plus.QueryBuilderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zhouyu74748585@hotmail.com
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

    public <T> List<T> convertToList(Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) {
        return function.apply(tClass, resultList);
    }

    public <T> List<T> convertToList(String typeName, BiFunction<String, List<Map<String, Object>>, List<T>> function) {
        return function.apply(typeName, resultList);
    }

    public <T> List<T> convertToList(Function<List<Map<String, Object>>, List<T>> function) {
        return function.apply(resultList);
    }

    private <T> PageRecord<T> listToPage(int pageIndex, int pageSize, long total, List<T> result) {
        int pages = Long.valueOf(total / pageSize + (total % pageSize == 0 ? 0 : 1)).intValue();
        PageRecord<T> pageRecord = new PageRecord<T>().setPageCount(pages)
                .setPageIndex(pageIndex)
                .setPageSize(pageSize)
                .setTotal(total);
        pageRecord.addAll(result);
        return pageRecord;
    }

    public <T> PageRecord<T> convertToPage(int pageIndex, int pageSize, long total, Class<T> tClass) throws Exception {
        List<T> result = QueryBuilderHelper.convert(tClass, resultList);
        return listToPage(pageIndex, pageSize, total, result);
    }

    public <T> PageRecord<T> convertToPage(int pageIndex, int pageSize, long total, Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) {
        List<T> result = function.apply(tClass, resultList);
        return listToPage(pageIndex, pageSize, total, result);
    }

    public <T> PageRecord<T> convertToPage(int pageIndex, int pageSize, long total, String typeName, BiFunction<String, List<Map<String, Object>>, List<T>> function) {
        List<T> result = function.apply(typeName, resultList);
        return listToPage(pageIndex, pageSize, total, result);
    }

    public <T> PageRecord<T> convertToPage(int pageIndex, int pageSize, long total, Function<List<Map<String, Object>>, List<T>> function) {
        List<T> result = function.apply(resultList);
        return listToPage(pageIndex, pageSize, total, result);
    }

    public <T> T convertToUnionOne(Function<List<Map<String, Object>>, T> function) {
        if (resultList.size() > 0) {
            return function.apply(resultList);
        } else {
            return null;
        }
    }

    public <T> T convertToUnionOne(T defaultValue, Function<List<Map<String, Object>>, T> function) {
        if (resultList.size() > 0) {
            return function.apply(resultList);
        } else {
            return defaultValue;
        }
    }
}
