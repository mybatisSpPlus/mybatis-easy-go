package com.mybatis.sp.plus;

import com.mybatis.sp.plus.conditions.Or;
import com.mybatis.sp.plus.meta.Result;
import com.mybatis.sp.plus.spring.BaseMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.mybatis.sp.plus.ActionMethods.*;
import static com.mybatis.sp.plus.MetaMethods.*;
import static com.mybatis.sp.plus.ConditionMethods.*;
import static com.mybatis.sp.plus.FunctionMethods.*;
/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 20:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {
    @Autowired
    BaseMapper baseMapper;

    @org.junit.Test
   public void selectTest1() throws Exception {
        Result result=select(field("*")).from(
                select(field("typeName"),count(constantField(1)).as("countNum")).from("AR_RESOURCE").groupBy("typeName")
                .having(
                        or(
                            and(
                                gt(field("countNum"),10),
                                lt(field("countNum"),100)
                            ),
                            gt(field("countNum"),1000)
                        )
                )
                .union().select(field("classification"),count(constantField("8")).as("countNum")).from("CEN_RES_INFO").groupBy("classification")
                .having(gt(field("countNum"),10)).asTable("t1"))
                .executeSelect();
       System.out.println(result.getResultList());
   }

    @org.junit.Test
    public void selectTest2() throws Exception {
        List<String> result=select().distinct("typeName").from("AR_RESOURCE").where(eq("status","1_DRAFT")).executeSelect().convertToList(String.class);
        System.out.println(result);
    }
}
