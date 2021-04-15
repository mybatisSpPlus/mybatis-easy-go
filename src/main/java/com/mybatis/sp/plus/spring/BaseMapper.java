package com.mybatis.sp.plus.spring;

import com.mybatis.sp.plus.step.Step;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 14:20
 */
@Mapper
public interface BaseMapper {

    List<Map<String, Object>> executeQuery(@Param("steps") List<Step> steps);

    int executeUpdate(@Param("steps") List<Step> steps);

    int executeInsert(@Param("steps") List<Step> steps);

    int executeDelete(@Param("steps") List<Step> steps);

    void execute(@Param("steps")List<Step> steps);
}
