package com.mybatis.sp.plus.spring;

import com.mybatis.sp.plus.Action;
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

    List<Map<String, Object>> executeQuery(@Param("actionChain") List<Action> actionChain);
}
