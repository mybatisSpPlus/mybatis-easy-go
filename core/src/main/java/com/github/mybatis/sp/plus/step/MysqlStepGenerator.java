package com.github.mybatis.sp.plus.step;

import com.github.mybatis.sp.plus.Action;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
public class MysqlStepGenerator extends StepGenerator {

    public MysqlStepGenerator(List<Action> actions) {
        super(actions,"`");
    }
}
