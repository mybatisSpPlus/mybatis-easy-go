package com.mybatis.sp.plus.spring;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author: create by zhouyu4034
 * @description: druid后台监控
 * @date:2020/6/18
 **/
@Configuration
public class DruidConfiguration {

    // ConfigurationProperties可以直接把应用配置的spring.datasource.druid属性开头的值注入到DruidDataSource中
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        // 添加druid的监控过滤器，当前只演示监控的功能，因此只有一个过滤器，可以实现多个过滤器
        LinkedList<Filter> filtersList = new LinkedList();
        filtersList.add(statFilter());
        filtersList.add(logFilter());
        druidDataSource.setProxyFilters(filtersList);
        return druidDataSource;
    }

    @Bean
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        // SQL执行时间超过2s种的被判定为慢日志
        statFilter.setSlowSqlMillis(2000);
        //显示慢日志
        statFilter.setLogSlowSql(true);
        //合并SQL，有时，一些相同的慢日志过多影响阅读，开启合并功能
        statFilter.setMergeSql(true);
        return statFilter;
    }

    @Bean
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setResultSetLogEnabled(false);
        filter.setConnectionLogEnabled(false);
        filter.setStatementParameterClearLogEnable(false);
        filter.setStatementCreateAfterLogEnabled(false);
        filter.setStatementCloseAfterLogEnabled(false);
        filter.setStatementParameterSetLogEnabled(false);
        filter.setStatementPrepareAfterLogEnabled(false);
        filter.setStatementLogErrorEnabled(true);
        filter.setStatementExecutableSqlLogEnable(true);
        return filter;
    }




}
