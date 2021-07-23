package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.actions.InsertInto;
import com.github.mybatis.easy.go.actions.Window;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;
import com.github.mybatis.easy.go.windowFunctions.*;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
@DatabaseVersion(maxVersion = "5.8")
public class Mysql5StepGenerator extends MysqlLikeStepGenerator {

    public Mysql5StepGenerator(List<Action> actions) {
        super(actions);
    }
}
