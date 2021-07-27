package com.github.mybatis.easy.go.spring;

import com.github.mybatis.easy.go.step.Step;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 14:20
 */
@Mapper
public interface BaseMapper {

    List<Map<String, Object>> executeQuery(@Param("sql") String sql, @Param("params") HashMap<String, Object> params);

    List<Map<String, Object>> executeFetchQuery(@Param("steps") List<Step> steps);

    int executeUpdate(@Param("sql") String sql, @Param("params") HashMap<String, Object> params);

    int executeInsert(@Param("sql") String sql, @Param("params") HashMap<String, Object> params);

    int executeDelete(@Param("sql") String sql, @Param("params") HashMap<String, Object> params);

    void execute(@Param("sql") String sql, @Param("params") HashMap<String, Object> params);
}
