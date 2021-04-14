package com.mybatis.sp.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 17:58
 */
@SpringBootApplication()
@EnableTransactionManagement
@MapperScan("com.mybatis.sp.plus.spring")
public class QBMain {
    public static void main(String[] args) {
        SpringApplication.run(QBMain.class, args);
    }
}
