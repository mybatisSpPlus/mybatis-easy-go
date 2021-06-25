package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
public class PgStepGenerator extends StepGenerator {

    public PgStepGenerator(List<Action> actions) {
        super(actions, "\"");
    }

}
